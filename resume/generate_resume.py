import os
import zipfile
from datetime import datetime


def xml_escape(text: str) -> str:
    return (
        text.replace("&", "&amp;")
        .replace("<", "&lt;")
        .replace(">", "&gt;")
        .replace('"', "&quot;")
        .replace("'", "&apos;")
    )


def normalize_for_pdf(text: str) -> str:
    replacements = {
        "–": "-",
        "—": "-",
        "•": "-",
        "’": "'",
        "“": '"',
        "”": '"',
        "©": "(c)",
    }
    for k, v in replacements.items():
        text = text.replace(k, v)
    # Remove other non-ASCII chars
    return ''.join(ch if 32 <= ord(ch) <= 126 else ' ' for ch in text)


def pdf_escape(text: str) -> str:
    text = text.replace("\\", "\\\\").replace("(", "\\(").replace(")", "\\)")
    return text


def build_docx_xml(name: str, location: str, phone: str, email: str,
                   summary: str, skills: list[str], experiences: list[dict], education: str) -> str:
    def p(text: str, bold: bool = False, size_half_points: int | None = None) -> str:
        runs = []
        rpr = []
        if bold:
            rpr.append("<w:b/>")
        if size_half_points is not None:
            rpr.append(f"<w:sz w:val=\"{size_half_points}\"/>")
            rpr.append(f"<w:szCs w:val=\"{size_half_points}\"/>")
        rpr_xml = f"<w:rPr>{''.join(rpr)}</w:rPr>" if rpr else ""
        runs.append(f"<w:r>{rpr_xml}<w:t xml:space=\"preserve\">{xml_escape(text)}</w:t></w:r>")
        return f"<w:p>{''.join(runs)}</w:p>"

    parts = []
    # Title
    parts.append(p(name, bold=True, size_half_points=36))  # 18pt
    parts.append(p(f"{location} | Phone: {phone} | Email: {email}", size_half_points=22))

    # Summary
    parts.append(p("PROFESSIONAL SUMMARY", bold=True))
    for line in summary.strip().split("\n"):
        if line.strip():
            parts.append(p(line.strip()))

    # Skills
    parts.append(p("CORE SKILLS", bold=True))
    parts.append(p(" \u2022 ".join(skills)))

    # Experience
    parts.append(p("EXPERIENCE", bold=True))
    for exp in experiences:
        header = f"{exp['company']} — {exp['title']} | {exp['location']} | {exp['dates']}"
        parts.append(p(header, bold=True))
        for b in exp.get('bullets', []):
            parts.append(p(f"• {b}"))

    # Education
    parts.append(p("EDUCATION", bold=True))
    parts.append(p(education))

    body_xml = "".join(parts)

    document_xml = f"""
    <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
    <w:document xmlns:wpc="http://schemas.microsoft.com/office/word/2010/wordprocessingCanvas"
        xmlns:mc="http://schemas.openxmlformats.org/markup-compatibility/2006"
        xmlns:o="urn:schemas-microsoft-com:office:office"
        xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships"
        xmlns:m="http://schemas.openxmlformats.org/officeDocument/2006/math"
        xmlns:v="urn:schemas-microsoft-com:vml"
        xmlns:wp14="http://schemas.microsoft.com/office/word/2010/wordprocessingDrawing"
        xmlns:wp="http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing"
        xmlns:w10="urn:schemas-microsoft-com:office:word"
        xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main"
        xmlns:w14="http://schemas.microsoft.com/office/word/2010/wordml"
        xmlns:wpg="http://schemas.microsoft.com/office/word/2010/wordprocessingGroup"
        xmlns:wpi="http://schemas.microsoft.com/office/word/2010/wordprocessingInk"
        xmlns:wne="http://schemas.microsoft.com/office/word/2006/wordml"
        xmlns:wps="http://schemas.microsoft.com/office/word/2010/wordprocessingShape"
        mc:Ignorable="w14 wp14">
      <w:body>
        {body_xml}
        <w:sectPr/>
      </w:body>
    </w:document>
    """.strip()
    return document_xml


def write_docx(output_path: str,
               name: str,
               location: str,
               phone: str,
               email: str,
               summary: str,
               skills: list[str],
               experiences: list[dict],
               education: str) -> None:
    document_xml = build_docx_xml(name, location, phone, email, summary, skills, experiences, education)

    content_types = """
    <?xml version="1.0" encoding="UTF-8"?>
    <Types xmlns="http://schemas.openxmlformats.org/package/2006/content-types">
      <Default Extension="rels" ContentType="application/vnd.openxmlformats-package.relationships+xml"/>
      <Default Extension="xml" ContentType="application/xml"/>
      <Override PartName="/word/document.xml" ContentType="application/vnd.openxmlformats-officedocument.wordprocessingml.document.main+xml"/>
      <Override PartName="/docProps/core.xml" ContentType="application/vnd.openxmlformats-package.core-properties+xml"/>
      <Override PartName="/docProps/app.xml" ContentType="application/vnd.openxmlformats-officedocument.extended-properties+xml"/>
    </Types>
    """.strip()

    rels_rels = """
    <?xml version="1.0" encoding="UTF-8"?>
    <Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships">
      <Relationship Id="rId1" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument" Target="word/document.xml"/>
      <Relationship Id="rId2" Type="http://schemas.openxmlformats.org/package/2006/relationships/metadata/core-properties" Target="docProps/core.xml"/>
      <Relationship Id="rId3" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/extended-properties" Target="docProps/app.xml"/>
    </Relationships>
    """.strip()

    now = datetime.utcnow().strftime("%Y-%m-%dT%H:%M:%SZ")

    core_xml = f"""
    <?xml version="1.0" encoding="UTF-8"?>
    <cp:coreProperties xmlns:cp="http://schemas.openxmlformats.org/package/2006/metadata/core-properties" xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:dcterms="http://purl.org/dc/terms/" xmlns:dcmitype="http://purl.org/dc/dcmitype/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
      <dc:title>{xml_escape(name)} - Resume</dc:title>
      <dc:creator>{xml_escape(name)}</dc:creator>
      <cp:lastModifiedBy>{xml_escape(name)}</cp:lastModifiedBy>
      <dcterms:created xsi:type="dcterms:W3CDTF">{now}</dcterms:created>
      <dcterms:modified xsi:type="dcterms:W3CDTF">{now}</dcterms:modified>
    </cp:coreProperties>
    """.strip()

    app_xml = """
    <?xml version="1.0" encoding="UTF-8"?>
    <Properties xmlns="http://schemas.openxmlformats.org/officeDocument/2006/extended-properties" xmlns:vt="http://schemas.openxmlformats.org/officeDocument/2006/docPropsVTypes">
      <Application>Resume Generator</Application>
      <DocSecurity>0</DocSecurity>
      <ScaleCrop>false</ScaleCrop>
      <HeadingPairs>
        <vt:vector size="2" baseType="variant">
          <vt:variant><vt:lpstr>Title</vt:lpstr></vt:variant>
          <vt:variant><vt:i4>1</vt:i4></vt:variant>
        </vt:vector>
      </HeadingPairs>
      <TitlesOfParts>
        <vt:vector size="1" baseType="lpstr">
          <vt:lpstr>Document</vt:lpstr>
        </vt:vector>
      </TitlesOfParts>
      <Company></Company>
      <LinksUpToDate>false</LinksUpToDate>
      <SharedDoc>false</SharedDoc>
      <HyperlinksChanged>false</HyperlinksChanged>
      <AppVersion>16.0000</AppVersion>
    </Properties>
    """.strip()

    with zipfile.ZipFile(output_path, 'w', compression=zipfile.ZIP_DEFLATED) as z:
        z.writestr('[Content_Types].xml', content_types)
        z.writestr('_rels/.rels', rels_rels)
        z.writestr('word/document.xml', document_xml)
        z.writestr('docProps/core.xml', core_xml)
        z.writestr('docProps/app.xml', app_xml)


def write_pdf(output_path: str, lines: list[tuple[str, int]]):
    # lines: list of (text, font_size)
    # Simple single-page PDF A4 (595 x 842)
    width, height = 595, 842

    def make_stream() -> bytes:
        contents = []
        y = 800
        for text, size in lines:
            safe_text = pdf_escape(normalize_for_pdf(text))
            # Choose font: headings use bold
            font = 'F2' if size >= 14 else 'F1'
            contents.append("BT\n")
            contents.append(f"/{font} {size} Tf\n")
            contents.append(f"1 0 0 1 50 {y} Tm\n")
            contents.append(f"({safe_text}) Tj\n")
            contents.append("ET\n")
            y -= int(size * 1.8)
            if y < 50:
                break
        return ''.join(contents).encode('latin-1', errors='ignore')

    stream_data = make_stream()

    # Build objects
    objects = []
    # 1: Catalog
    objects.append((1, b"<< /Type /Catalog /Pages 2 0 R >>"))
    # 2: Pages
    objects.append((2, b"<< /Type /Pages /Kids [3 0 R] /Count 1 >>"))
    # 3: Page
    page_dict = b"<< /Type /Page /Parent 2 0 R /MediaBox [0 0 %d %d] /Contents 4 0 R /Resources << /Font << /F1 5 0 R /F2 6 0 R >> >> >>" % (width, height)
    objects.append((3, page_dict))
    # 4: Contents
    objects.append((4, b"<< /Length %d >>\nstream\n" % len(stream_data) + stream_data + b"endstream"))
    # 5: Font Helvetica
    objects.append((5, b"<< /Type /Font /Subtype /Type1 /BaseFont /Helvetica >>"))
    # 6: Font Helvetica-Bold
    objects.append((6, b"<< /Type /Font /Subtype /Type1 /BaseFont /Helvetica-Bold >>"))

    # Write PDF
    with open(output_path, 'wb') as f:
        f.write(b"%PDF-1.4\n")
        xref_positions = [0]
        for obj_num, obj_content in objects:
            xref_positions.append(f.tell())
            f.write(f"{obj_num} 0 obj\n".encode('ascii'))
            f.write(obj_content)
            f.write(b"\nendobj\n")
        xref_start = f.tell()
        f.write(f"xref\n0 {len(objects)+1}\n".encode('ascii'))
        # object 0
        f.write(b"0000000000 65535 f \n")
        for pos in xref_positions[1:]:
            f.write(f"{pos:010d} 00000 n \n".encode('ascii'))
        f.write(b"trailer\n")
        trailer = f"<< /Size {len(objects)+1} /Root 1 0 R >>\n"
        f.write(trailer.encode('ascii'))
        f.write(b"startxref\n")
        f.write(f"{xref_start}\n".encode('ascii'))
        f.write(b"%%EOF")


if __name__ == "__main__":
    out_dir = "/workspace/resume"
    os.makedirs(out_dir, exist_ok=True)

    name = "Maneesh Sai Swaroop Akkineni"
    location = "Hyderabad, India"
    phone = "XXXXX"
    email = "XXXXX"

    summary = (
        "I am a passionate Java developer focused on improving my expertise and applying it effectively at work. "
        "I have hands-on exposure to sustainability-focused products and teams, including ESG, ESG Insights, "
        "Building Circularity Index (BCI), and Ubigreen Integration. I am confident in my Java skills and "
        "hope to contribute to society through my work."
    )

    skills = ["Java", "Spring"]

    experiences = [
        {
            "company": "Planon Software",
            "title": "Developer",
            "location": "Hyderabad",
            "dates": "Jul 2023 - Present",
            "bullets": [
                "Worked across ESG, ESG Insights, BCI, and Ubigreen teams; currently focused on Ubigreen integration.",
                "Contributed to integrations and services supporting sustainability reporting and analytics.",
            ],
        },
        {
            "company": "Planon Software",
            "title": "Associate Developer",
            "location": "Hyderabad",
            "dates": "Nov 2020 - Jul 2023",
            "bullets": [
                "Worked in Space & Workplace Management (SWM) product and project teams.",
                "Collaborated with the ESG team on related capabilities and workflows.",
            ],
        },
        {
            "company": "Planon Software",
            "title": "Intern",
            "location": "Hyderabad",
            "dates": "Dec 2019 - Nov 2020",
            "bullets": [
                "Improved Java knowledge, completed a hotel management case, and joined the SWM team.",
            ],
        },
        {
            "company": "Comakeit",
            "title": "Intern",
            "location": "Hyderabad",
            "dates": "Jul 2019 - Dec 2019",
            "bullets": [
                "Worked on a Service Ticket Resolution System while learning Java and Angular basics.",
            ],
        },
    ]

    education = "B.Tech in CMR College of Engineering and Technology (2016 - 2020)"

    docx_path = os.path.join(out_dir, "Maneesh_Sai_Swaroop_Akkineni_Resume.docx")
    pdf_path = os.path.join(out_dir, "Maneesh_Sai_Swaroop_Akkineni_Resume.pdf")

    write_docx(docx_path, name, location, phone, email, summary, skills, experiences, education)

    # Build PDF line list with font sizes
    pdf_lines: list[tuple[str, int]] = []
    pdf_lines.append((name, 18))
    pdf_lines.append((f"{location} | Phone: {phone} | Email: {email}", 10))
    pdf_lines.append(("", 8))
    pdf_lines.append(("PROFESSIONAL SUMMARY", 12))
    for part in summary.split(". "):
        if part.strip():
            pdf_lines.append((part.strip(), 10))
    pdf_lines.append(("", 8))
    pdf_lines.append(("CORE SKILLS", 12))
    pdf_lines.append(("Java, Spring", 10))
    pdf_lines.append(("", 8))
    pdf_lines.append(("EXPERIENCE", 12))
    for exp in experiences:
        header = f"{exp['company']} - {exp['title']} | {exp['location']} | {exp['dates']}"
        pdf_lines.append((header, 11))
        for b in exp.get('bullets', []):
            pdf_lines.append((f"- {b}", 10))
    pdf_lines.append(("", 8))
    pdf_lines.append(("EDUCATION", 12))
    pdf_lines.append((education, 10))

    write_pdf(pdf_path, pdf_lines)

    print(f"Generated: {docx_path}\nGenerated: {pdf_path}")
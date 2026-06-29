package com.kata.books.services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.kata.books.domain.Livro;
import com.kata.books.domain.dtos.AutorLivrosDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class AutorLivrosPDF {

	@Autowired
	private LivroService autorLivrosService; // serviço que retorna a lista de AutorLivrosDTO

	public byte[] gerarRelatorioPDF() throws DocumentException {
		List<AutorLivrosDTO> lista = autorLivrosService.findAutorbyLivros();

		Document document = new Document(PageSize.A4.rotate());
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		try {
			PdfWriter.getInstance(document, outputStream);
			document.open();

			// Título
			Font fonteTitulo = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
			Paragraph titulo = new Paragraph("RELATÓRIO DE AUTORES E LIVROS", fonteTitulo);
			titulo.setAlignment(Element.ALIGN_CENTER);
			document.add(titulo);

			// Data e hora
			Font fonteData = new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC);
			LocalDateTime agora = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
			Paragraph data = new Paragraph("Gerado em: " + agora.format(formatter), fonteData);
			data.setAlignment(Element.ALIGN_RIGHT);
			document.add(data);

			document.add(new Paragraph(" "));

			// Tabela com 3 colunas
			PdfPTable table = new PdfPTable(3);
			table.setWidthPercentage(100);
			table.setSpacingBefore(10f);

			float[] colunasLargura = {2f, 4f, 4f};
			table.setWidths(colunasLargura);

			// Cabeçalho
			Font fonteCabecalho = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, BaseColor.WHITE);
			BaseColor corCabecalho = new BaseColor(51, 102, 153);

			String[] cabecalhos = {"Autor", "Livros", "Assuntos"};

			for (String cabecalho : cabecalhos) {
				PdfPCell celula = new PdfPCell(new Phrase(cabecalho, fonteCabecalho));
				celula.setBackgroundColor(corCabecalho);
				celula.setHorizontalAlignment(Element.ALIGN_CENTER);
				celula.setPadding(10);
				table.addCell(celula);
			}

			// Dados
			Font fonteDados = new Font(Font.FontFamily.HELVETICA, 10);

			if (lista != null && !lista.isEmpty()) {
				for (AutorLivrosDTO dto : lista) {
					table.addCell(new PdfPCell(new Phrase(dto.getAutor() != null ? dto.getAutor() : "-", fonteDados)));
					table.addCell(new PdfPCell(new Phrase(dto.getLivros() != null ? dto.getLivros() : "-", fonteDados)));
					table.addCell(new PdfPCell(new Phrase(dto.getAssuntos() != null ? dto.getAssuntos() : "-", fonteDados)));
				}
			} else {
				PdfPCell celulaVazia = new PdfPCell(new Phrase("Nenhum autor/livro cadastrado", fonteDados));
				celulaVazia.setColspan(3);
				celulaVazia.setHorizontalAlignment(Element.ALIGN_CENTER);
				celulaVazia.setPadding(10);
				table.addCell(celulaVazia);
			}

			document.add(table);

			// Resumo
			document.add(new Paragraph(" "));
			Font fonteResumo = new Font(Font.FontFamily.HELVETICA, 10);

		} finally {
			if (document.isOpen()) {
				document.close();
			}
		}

		return outputStream.toByteArray();
	}
}

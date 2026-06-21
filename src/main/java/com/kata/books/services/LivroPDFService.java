package com.kata.books.services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.kata.books.domain.Livro;
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
public class LivroPDFService {

	@Autowired
	private LivroService livroService;

	public byte[] gerarRelatorioPDF() throws DocumentException {
		List<Livro> livros = livroService.findAll();

		Document document = new Document(PageSize.A4.rotate());
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		try {
			PdfWriter.getInstance(document, outputStream);
			document.open();

			// Título
			Font fonteTitulo = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
			Paragraph titulo = new Paragraph("RELATÓRIO DE LIVROS", fonteTitulo);
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

			// Tabela com 9 colunas
			PdfPTable table = new PdfPTable(9);
			table.setWidthPercentage(100);
			table.setSpacingBefore(10f);

			float[] colunasLargura = {0.8f, 2.5f, 1.5f, 1f, 1f, 1.3f, 1.3f, 2f, 2f};
			table.setWidths(colunasLargura);

			// Cabeçalho
			Font fonteCabecalho = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, BaseColor.WHITE);
			BaseColor corCabecalho = new BaseColor(51, 102, 153);

			String[] cabecalhos = {"ID", "Título", "Editora", "Edição", "Ano", "Valor", "Quant.", "Assunto", "Autores" };

			for (String cabecalho : cabecalhos) {
				PdfPCell celula = new PdfPCell(new Phrase(cabecalho, fonteCabecalho));
				celula.setBackgroundColor(corCabecalho);
				celula.setHorizontalAlignment(Element.ALIGN_CENTER);
				celula.setPadding(10);
				table.addCell(celula);
			}

			// Dados
			Font fonteDados = new Font(Font.FontFamily.HELVETICA, 10);

			if (livros != null && !livros.isEmpty()) {
				for (Livro livro : livros) {
					PdfPCell celulaId = new PdfPCell(new Phrase(livro.getId().toString(), fonteDados));
					celulaId.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(celulaId);

					table.addCell(new PdfPCell(new Phrase(livro.getTitulo() != null ? livro.getTitulo() : "-", fonteDados)));
					table.addCell(new PdfPCell(new Phrase(livro.getEditora() != null ? livro.getEditora() : "-", fonteDados)));

					PdfPCell celulaEdicao = new PdfPCell(new Phrase(livro.getEdicao() != null ? livro.getEdicao().toString() : "-", fonteDados));
					celulaEdicao.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(celulaEdicao);

					PdfPCell celulaAno = new PdfPCell(new Phrase(livro.getAnoPublicacao() != null ? livro.getAnoPublicacao() : "-", fonteDados));
					celulaAno.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(celulaAno);

					// Valor formatado
					NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
					String valorFormatado = (livro.getValor() != null ? nf.format(livro.getValor()) : "-");
					PdfPCell celulaValor = new PdfPCell(new Phrase(valorFormatado, fonteDados));
					celulaValor.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table.addCell(celulaValor);

					// Quantidade
					PdfPCell celulaQuantidade = new PdfPCell(new Phrase(
							livro.getQuantidade() != null ? livro.getQuantidade().toString() : "-", fonteDados));
					celulaQuantidade.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(celulaQuantidade);

					String assuntoNome = (livro.getAssunto() != null && livro.getAssunto().getDescricao() != null)
							? livro.getAssunto().getDescricao() : "-";
					table.addCell(new PdfPCell(new Phrase(assuntoNome, fonteDados)));

					String autores = "-";
					if (livro.getAutores() != null && !livro.getAutores().isEmpty()) {
						autores = livro.getAutores()
								.stream()
								.map(a -> a.getNome())
								.collect(Collectors.joining(", "));
					}
					table.addCell(new PdfPCell(new Phrase(autores, fonteDados)));


				}
			} else {
				PdfPCell celulaVazia = new PdfPCell(new Phrase("Nenhum livro cadastrado", fonteDados));
				celulaVazia.setColspan(9);
				celulaVazia.setHorizontalAlignment(Element.ALIGN_CENTER);
				celulaVazia.setPadding(10);
				table.addCell(celulaVazia);
			}

			document.add(table);

			// Resumo
			document.add(new Paragraph(" "));
			Font fonteResumo = new Font(Font.FontFamily.HELVETICA, 10);

			int totalQuantidade = 0;
			BigDecimal valorTotal = BigDecimal.ZERO;
			NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

			if (livros != null && !livros.isEmpty()) {
				for (Livro livro : livros) {
					if (livro.getQuantidade() != null) {
						totalQuantidade += livro.getQuantidade();
					}
					if (livro.getValor() != null && livro.getQuantidade() != null) {
						valorTotal = valorTotal.add(livro.getValor().multiply(new BigDecimal(livro.getQuantidade())));
					}
				}
			}

			Paragraph resumoLivros = new Paragraph("Total de Livros: " + (livros != null ? livros.size() : 0), fonteResumo);
			resumoLivros.setAlignment(Element.ALIGN_RIGHT);
			document.add(resumoLivros);

			Paragraph resumoQuantidade = new Paragraph("Quantidade Total: " + totalQuantidade, fonteResumo);
			resumoQuantidade.setAlignment(Element.ALIGN_RIGHT);
			document.add(resumoQuantidade);

			Paragraph resumoValor = new Paragraph("Valor Total: " + nf.format(valorTotal), fonteResumo);
			resumoValor.setAlignment(Element.ALIGN_RIGHT);
			document.add(resumoValor);

		} finally {
			if (document.isOpen()) {
				document.close();
			}
		}

		return outputStream.toByteArray();
	}
}

package com.kata.books.services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.kata.books.domain.Livro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
			
			// Adiciona título
			Font fonteTitulo = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
			Paragraph titulo = new Paragraph("RELATÓRIO DE LIVROS", fonteTitulo);
			titulo.setAlignment(Element.ALIGN_CENTER);
			document.add(titulo);
			
			// Adiciona data e hora
			Font fonteData = new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC);
			LocalDateTime agora = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
			Paragraph data = new Paragraph("Gerado em: " + agora.format(formatter), fonteData);
			data.setAlignment(Element.ALIGN_RIGHT);
			document.add(data);
			
			document.add(new Paragraph(" "));
			
			// Cria tabela com 7 colunas
			PdfPTable table = new PdfPTable(7);
			table.setWidthPercentage(100);
			table.setSpacingBefore(10f);
			
			// Define largura das colunas
			float[] colunasLargura = {1f, 2.5f, 1.5f, 1f, 1f, 1.5f, 1.5f};
			table.setWidths(colunasLargura);
			
			// Adiciona cabeçalho da tabela
			Font fonteCabecalho = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, BaseColor.WHITE);
			BaseColor corCabecalho = new BaseColor(51, 102, 153);
			
			String[] cabecalhos = {"ID", "Título", "Editora", "Edição", "Ano", "Assunto", "Autores"};
			
			for (String cabecalho : cabecalhos) {
				PdfPCell celula = new PdfPCell(new Phrase(cabecalho, fonteCabecalho));
				celula.setBackgroundColor(corCabecalho);
				celula.setHorizontalAlignment(Element.ALIGN_CENTER);
				celula.setPadding(10);
				table.addCell(celula);
			}
			
			// Adiciona dados dos livros
			Font fonteDados = new Font(Font.FontFamily.HELVETICA, 10);
			
			if (livros != null && !livros.isEmpty()) {
				for (Livro livro : livros) {
					// ID
					PdfPCell celulaID = new PdfPCell(new Phrase(livro.getId().toString(), fonteDados));
					celulaID.setPadding(8);
					table.addCell(celulaID);
					
					// Título
					PdfPCell celulaTitulo = new PdfPCell(new Phrase(livro.getTitulo() != null ? livro.getTitulo() : "-", fonteDados));
					celulaTitulo.setPadding(8);
					table.addCell(celulaTitulo);
					
					// Editora
					PdfPCell celulaEditora = new PdfPCell(new Phrase(livro.getEditora() != null ? livro.getEditora() : "-", fonteDados));
					celulaEditora.setPadding(8);
					table.addCell(celulaEditora);
					
					// Edição
					PdfPCell celulaEdicao = new PdfPCell(new Phrase(livro.getEdicao() != null ? livro.getEdicao().toString() : "-", fonteDados));
					celulaEdicao.setPadding(8);
					celulaEdicao.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(celulaEdicao);
					
					// Ano Publicação
					PdfPCell celulaAno = new PdfPCell(new Phrase(livro.getAnoPublicacao() != null ? livro.getAnoPublicacao() : "-", fonteDados));
					celulaAno.setPadding(8);
					celulaAno.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(celulaAno);
					
					// Assunto
					String assuntoNome = (livro.getAssunto() != null && livro.getAssunto().getDescricao() != null) 
						? livro.getAssunto().getDescricao() 
						: "-";
					PdfPCell celulaAssunto = new PdfPCell(new Phrase(assuntoNome, fonteDados));
					celulaAssunto.setPadding(8);
					table.addCell(celulaAssunto);
					
					// Autores
					String autores = "-";
					if (livro.getAutores() != null && !livro.getAutores().isEmpty()) {
						StringBuilder sb = new StringBuilder();
						for (int i = 0; i < livro.getAutores().size(); i++) {
							if (i > 0) sb.append(", ");
							sb.append(livro.getAutores().get(i).getNome());
						}
						autores = sb.toString();
					}
					PdfPCell celulaAutores = new PdfPCell(new Phrase(autores, fonteDados));
					celulaAutores.setPadding(8);
					table.addCell(celulaAutores);
				}
			} else {
				// Adiciona linha vazia se não houver livros
				PdfPCell celulaVazia = new PdfPCell(new Phrase("Nenhum livro cadastrado", fonteDados));
				celulaVazia.setColspan(7);
				celulaVazia.setHorizontalAlignment(Element.ALIGN_CENTER);
				celulaVazia.setPadding(10);
				table.addCell(celulaVazia);
			}
			
			document.add(table);
			
			// Adiciona resumo
			document.add(new Paragraph(" "));
			Font fonteResumo = new Font(Font.FontFamily.HELVETICA, 10);
			Paragraph resumo = new Paragraph("Total de Livros: " + (livros != null ? livros.size() : 0), fonteResumo);
			resumo.setAlignment(Element.ALIGN_RIGHT);
			document.add(resumo);
			
		} finally {
			if (document.isOpen()) {
				document.close();
			}
		}
		
		return outputStream.toByteArray();
	}
}

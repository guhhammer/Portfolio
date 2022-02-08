

#Você deve executar o comando: pip install xlsxwriter no seu prompt   se quiser executar este arquivo
import xlsxwriter



workbook = xlsxwriter.Workbook("C:\\Users\\Gustavo\\Desktop\\Semana8\\projetoSemana8\\src\\ParalelismoAula1\\Paralelismo.xlsx")


scoresA1 = (["N(2^tamanho)", "Tempo"],[15, 1.16],[16, 0.8],[17, 1.08],[18, 1.92],[19, 3.16],[20, 5.6],[21, 9.72],[22, 19.12],[23, 37.76],[24, 74.4],[25, 176.98],[26, 322.42],[27, 684.38],[28, 1294.52])

scoresA2 = (["N(2^tamanho)", "Tempo"],[15, 1.0],[16, 1.1],[17, 1.48],[18, 2.12],[19, 2.98],[20, 5.42],[21, 10.5],[22, 19.54],[23, 36.98],[24, 75.28],[25, 169.04],[26, 343.72],[27, 702.0],[28, 1341.22])

scoresB1 = (["N(2^tamanho máximo)", "N. Processadores", "Tempo"],[28, 1, 3779.52],[28, 2, 1985.24],[28, 3, 1452.06],[28, 4, 1342.06])

scoresB2 = (["N(2^tamanho máximo)", "N. Processadores", "Tempo"],[28, 1, 1985.2],[28, 2, 1321.24],[28, 3, 1290.52],[28, 4, 1297.58])

scoresC1 = (["N(2^tamanho máximo)", "N. Processadores", "Speed UP"],[28, 1, 1],[28, 2, 0.9665963855421686],[28, 3, 1.3396037260335008],[28, 4, 1.6810123061948132])

scoresC2 = (["N(2^tamanho máximo)", "N. Processadores", "Speed UP"],[28, 1, 1],[28, 2, 1.6785521715687128],[28, 3, 1.6237535372591296],[28, 4, 1.6381851328097048])

scoresD1 = (["N(2^tamanho máximo)", "N. Processadores", "Eficiencia"],[28, 1, 1],[28, 2, 0.48188529701675686],[28, 3, 0.4455784680934014],[28, 4, 0.4193372622181926])

scoresD2 = (["N(2^tamanho máximo)", "N. Processadores", "Eficiencia"],[28, 1, 1],[28, 2, 0.835450286309214],[28, 3, 0.5464202684586537],[28, 4, 0.40972821945643895])


worksheetA1 = workbook.add_worksheet("P_A1")
rowA1 = 0
colA1 = 0
for x, y  in (scoresA1):
	colA1 = 0
	worksheetA1.write(rowA1, colA1, x) 
	worksheetA1.write(rowA1, colA1 + 1, y) 
	rowA1 += 1


chartA1 = workbook.add_chart({'type': 'line'})

chartA1.add_series({
	'name': 'Planilha A1(P_A1)',
	'categories': 'P_A1!$A$2:$A$15',
	'values': 'P_A1!$B$2:$B$15',
	'data_labels': {'value': True, 'num_format': '#,##0.00'},
	'line': {'color': 'orange', 'width': 2.25},
	'marker': {
		'type': 'circle',
		'size': 6,
		'border': {'color': 'black'},
		'fill': {'color': 'blue'},
	},
})


chartA1.set_title({'name': 'Planilha A1(P_A1)'})
chartA1.set_x_axis({'name': 'N(2^tamanho)'})
chartA1.set_y_axis({'name': 'Tempo de Execução'})


chartA1.set_style(9)
worksheetA1.insert_chart('D2',chartA1, {'x_offset': 25, 'y_offset': 10})


worksheetA2 = workbook.add_worksheet("P_A2")
rowA2 = 0
colA2 = 0
for x, y  in (scoresA2):
	colA2 = 0
	worksheetA2.write(rowA2, colA2, x) 
	worksheetA2.write(rowA2, colA2 + 1, y) 
	rowA2 += 1


chartA2 = workbook.add_chart({'type': 'line'})

chartA2.add_series({
	'name': 'Planilha A2(P_A2)',
	'categories': 'P_A2!$A$2:$A$15',
	'values': 'P_A2!$B$2:$B$15',
	'data_labels': {'value': True, 'num_format': '#,##0.00'},
	'line': {'color': 'orange', 'width': 2.25},
	'marker': {
		'type': 'circle',
		'size': 6,
		'border': {'color': 'black'},
		'fill': {'color': 'blue'},
	},
})


chartA2.set_title({'name': 'Planilha A2(P_A2)'})
chartA2.set_x_axis({'name': 'N(2^tamanho)'})
chartA2.set_y_axis({'name': 'Tempo de Execução'})


chartA2.set_style(9)
worksheetA2.insert_chart('D2',chartA2, {'x_offset': 25, 'y_offset': 10})


worksheetB1 = workbook.add_worksheet("P_B1")
rowB1 = 0
colB1 = 0
for x, y, z  in (scoresB1):
	colB1 = 0
	worksheetB1.write(rowB1, colB1, x) 
	worksheetB1.write(rowB1, colB1 + 1, y) 
	worksheetB1.write(rowB1, colB1 + 2, z) 
	rowB1 += 1


chartB1 = workbook.add_chart({'type': 'line'})

chartB1.add_series({
	'name': 'Planilha B1(P_B1)',
	'categories': 'P_B1!$B$2:$B$5',
	'values': 'P_B1!$C$2:$C$5',
	'data_labels': {'value': True, 'num_format': '#,##0.00'},
	'smooth': True,
	'line': {'color': 'orange', 'width': 2.25},
	'marker': {
		'type': 'circle',
		'size': 6,
		'border': {'color': 'black'},
		'fill': {'color': 'blue'},
	},
})


chartB1.set_title({'name': 'Planilha B1(P_B1)'})
chartB1.set_x_axis({'name': 'P_B1!$B$1'})
chartB1.set_y_axis({'name': 'P_B1!$C$1'})


chartB1.set_style(5)
worksheetB1.insert_chart('E2',chartB1, {'x_offset': 25, 'y_offset': 10})


worksheetB2 = workbook.add_worksheet("P_B2")
rowB2 = 0
colB2 = 0
for x, y, z  in (scoresB2):
	colB2 = 0
	worksheetB2.write(rowB2, colB2, x) 
	worksheetB2.write(rowB2, colB2 + 1, y) 
	worksheetB2.write(rowB2, colB2 + 2, z) 
	rowB2 += 1


chartB2 = workbook.add_chart({'type': 'line'})

chartB2.add_series({
	'name': 'Planilha B2(P_B2)',
	'categories': 'P_B2!$B$2:$B$5',
	'values': 'P_B2!$C$2:$C$5',
	'data_labels': {'value': True, 'num_format': '#,##0.00'},
	'smooth': True,
	'line': {'color': 'orange', 'width': 2.25},
	'marker': {
		'type': 'circle',
		'size': 6,
		'border': {'color': 'black'},
		'fill': {'color': 'blue'},
	},
})


chartB2.set_title({'name': 'Planilha B2(P_B2)'})
chartB2.set_x_axis({'name': 'P_B2!$B$1'})
chartB2.set_y_axis({'name': 'P_B2!$C$1'})


chartB2.set_style(5)
worksheetB2.insert_chart('E2',chartB2, {'x_offset': 25, 'y_offset': 10})


worksheetC1 = workbook.add_worksheet("P_C1")
rowC1 = 0
colC1 = 0
for x, y, z  in (scoresC1):
	colC1 = 0
	worksheetC1.write(rowC1, colC1, x) 
	worksheetC1.write(rowC1, colC1 + 1, y) 
	worksheetC1.write(rowC1, colC1 + 2, z) 
	rowC1 += 1


chartC1 = workbook.add_chart({'type': 'line'})

chartC1.add_series({
	'name': 'Planilha C1(P_C1)',
	'categories': 'P_C1!$B$2:$B$5',
	'values': 'P_C1!$C$2:$C$5',
	'data_labels': {'value': True, 'num_format': '#,##0.00'},
	'smooth': True,
	'line': {'color': 'orange', 'width': 2.25},
	'marker': {
		'type': 'circle',
		'size': 6,
		'border': {'color': 'black'},
		'fill': {'color': 'blue'},
	},
})


chartC1.set_title({'name': 'Planilha C1(P_C1)'})
chartC1.set_x_axis({'name': 'P_C1!$B$1'})
chartC1.set_y_axis({'name': 'P_C1!$C$1'})


chartC1.set_style(5)
worksheetC1.insert_chart('E2',chartC1, {'x_offset': 25, 'y_offset': 10})


worksheetC2 = workbook.add_worksheet("P_C2")
rowC2 = 0
colC2 = 0
for x, y, z  in (scoresC2):
	colC2 = 0
	worksheetC2.write(rowC2, colC2, x) 
	worksheetC2.write(rowC2, colC2 + 1, y) 
	worksheetC2.write(rowC2, colC2 + 2, z) 
	rowC2 += 1


chartC2 = workbook.add_chart({'type': 'line'})

chartC2.add_series({
	'name': 'Planilha C2(P_C2)',
	'categories': 'P_C2!$B$2:$B$5',
	'values': 'P_C2!$C$2:$C$5',
	'data_labels': {'value': True, 'num_format': '#,##0.00'},
	'smooth': True,
	'line': {'color': 'orange', 'width': 2.25},
	'marker': {
		'type': 'circle',
		'size': 6,
		'border': {'color': 'black'},
		'fill': {'color': 'blue'},
	},
})


chartC2.set_title({'name': 'Planilha C2(P_C2)'})
chartC2.set_x_axis({'name': 'P_C2!$B$1'})
chartC2.set_y_axis({'name': 'P_C2!$C$1'})


chartC2.set_style(5)
worksheetC2.insert_chart('E2',chartC2, {'x_offset': 25, 'y_offset': 10})


worksheetD1 = workbook.add_worksheet("P_D1")
rowD1 = 0
colD1 = 0
for x, y, z  in (scoresD1):
	colD1 = 0
	worksheetD1.write(rowD1, colD1, x) 
	worksheetD1.write(rowD1, colD1 + 1, y) 
	worksheetD1.write(rowD1, colD1 + 2, z) 
	rowD1 += 1


chartD1 = workbook.add_chart({'type': 'line'})

chartD1.add_series({
	'name': 'Planilha D1(P_D1)',
	'categories': 'P_D1!$B$2:$B$5',
	'values': 'P_D1!$C$2:$C$5',
	'data_labels': {'value': True, 'num_format': '#,##0.00'},
	'smooth': True,
	'line': {'color': 'orange', 'width': 2.25},
	'marker': {
		'type': 'circle',
		'size': 6,
		'border': {'color': 'black'},
		'fill': {'color': 'blue'},
	},
})


chartD1.set_title({'name': 'Planilha D1(P_D1)'})
chartD1.set_x_axis({'name': 'P_D1!$B$1'})
chartD1.set_y_axis({'name': 'P_D1!$C$1'})


chartD1.set_style(5)
worksheetD1.insert_chart('E2',chartD1, {'x_offset': 25, 'y_offset': 10})


worksheetD2 = workbook.add_worksheet("P_D2")
rowD2 = 0
colD2 = 0
for x, y, z  in (scoresD2):
	colD2 = 0
	worksheetD2.write(rowD2, colD2, x) 
	worksheetD2.write(rowD2, colD2 + 1, y) 
	worksheetD2.write(rowD2, colD2 + 2, z) 
	rowD2 += 1


chartD2 = workbook.add_chart({'type': 'line'})

chartD2.add_series({
	'name': 'Planilha D2(P_D2)',
	'categories': 'P_D2!$B$2:$B$5',
	'values': 'P_D2!$C$2:$C$5',
	'data_labels': {'value': True, 'num_format': '#,##0.00'},
	'smooth': True,
	'line': {'color': 'orange', 'width': 2.25},
	'marker': {
		'type': 'circle',
		'size': 6,
		'border': {'color': 'black'},
		'fill': {'color': 'blue'},
	},
})


chartD2.set_title({'name': 'Planilha D2(P_D2)'})
chartD2.set_x_axis({'name': 'P_D2!$B$1'})
chartD2.set_y_axis({'name': 'P_D2!$C$1'})


chartD2.set_style(5)
worksheetD2.insert_chart('E2',chartD2, {'x_offset': 25, 'y_offset': 10})


workbook.close()

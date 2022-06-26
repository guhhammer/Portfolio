

import xlsxwriter



workbook = xlsxwriter.Workbook("C:\\Users\\Gustavo\\Documents\\NetBeansProjects\\Programação Paralela, Concorrente e Distribuída\\src\\ParalelismoRecursivo\\ParalelismoRecursivo.xlsx")


scoresNums = ([15.0, 6.14, 3.82],[16.0, 10.58, 6.1],[17.0, 21.82, 10.66],[18.0, 47.14, 22.0],[19.0, 96.98, 43.0],[20.0, 245.28, 111.74],[21.0, 495.62, 208.4],[22.0, 990.18, 427.28],[23.0, 2198.38, 914.04],[24.0, 4113.38, 1754.38],[25.0, 8163.64, 3623.82],[26.0, 16838.32, 7635.68])


worksheetNums = workbook.add_worksheet("P_Nums")


worksheetNums.write(0,0, "2^N")
worksheetNums.write(0,1, "Tempo Sequencial")
worksheetNums.write(0,2, "Tempo Paralelo")


rowNums = 1
colNums = 0
for x, y, z  in (scoresNums):
	colNums = 0
	worksheetNums.write(rowNums, colNums, x) 
	worksheetNums.write(rowNums, colNums + 1, y) 
	worksheetNums.write(rowNums, colNums + 2, z) 
	rowNums += 1


chartNums = workbook.add_chart({'type': 'line'})

chartNums.add_series({
	'name': 'Tempo Sequencial',
	'categories': 'P_Nums!$A$2:$A$13',
	'values': 'P_Nums!$B$2:$B$13',
	'data_labels': {'value': True, 'num_format': '#,##0.00'},
	'line': {'color': 'orange', 'width': 2.25},
	'marker': {
		'type': 'circle',
		'size': 6,
		'border': {'color': 'black'},
		'fill': {'color': 'blue'},
	},
})
chartNums.add_series({
	'name': 'Tempo Paralelo',
	'categories': 'P_Nums!$A$2:$A$13',
	'values': 'P_Nums!$C$2:$C$13',
	'data_labels': {'value': True, 'num_format': '#,##0.00'},
	'line': {'color': 'green', 'width': 2.25},
	'marker': {
		'type': 'circle',
		'size': 6,
		'border': {'color': 'black'},
		'fill': {'color': 'blue'},
	},
})


chartNums.set_title({'name': 'Planilha Nums(P_Nums)'})
chartNums.set_x_axis({'name': '2^N'})
chartNums.set_y_axis({'name': 'Tempo de Execução(mS) '})


chartNums.set_style(9)
worksheetNums.insert_chart('E3',chartNums, {'x_offset': 25, 'y_offset': 10})


workbook.close()

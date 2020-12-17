Para llamar a este componente se debera agregar el tag
<jhi-pie-chart
    [pieChartData]="data"
    [pieChartLabels]="Labels"
    [width]="400">
</jhi-pie-chart>

---donde data es una lista de ints o floatsde manera:
    [ int, int ]
    Ejemplo:
    [300, 500, 100]

---donde labels es una lista de strings de manera:
    ['string', 'string']
    Ejemplo:
    ['Download', 'Store', 'Mail Sales']

---donde width es el ancho del chart la altura se define automaticamente dependiendo del width

Para llamar a este componente se debera agregar el tag

<jhi-bar-chart
    [barChartData]="data"
    [barChartLabels]="labels" [width]="900">
</jhi-bar-chart>

---donde data es una lista de objetos json de manera:
    { data: ["datos que se van a visualizar en floats o ints"], label: 'Nombre del Section'}
    Ejemplo:
    [
        { data: [65, 59, 80, 81, 56, 55, 40], label: 'Series A' },
        { data: [28, 48, 40, 19, 86, 27, 90], label: 'Series B' }
    ]

---donde labels es una lista de strings de manera:
    ['string', 'string']
    Ejemplo:
    ['2006', '2007', '2008', '2009', '2010', '2011', '2012']

---donde width es el ancho del chart la altura se define automaticamente dependiendo del width

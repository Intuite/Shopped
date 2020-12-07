Para llamar a este componente se debera agregar el tag
<jhi-line-chart [ChartData]="data"
    [ChartLabels]="labels"
    [width]="100">
</jhi-line-chart>

---donde data es una lista de objetos json de manera:
    { data: ["datos que se van a visualizar en floats o ints"], label: 'Nombre del Section'}
    Ejemplo:
    [
        { data: [10, 15], label: 'Section A'},
        { data: [15, 20], label: 'Section B'}
    ]

---donde labels es una lista de strings de manera:
    ['string', 'string']
    Ejemplo:
    ['Mon', 'Tuesday']

---donde width es el ancho del chart la altura se define automaticamente dependiendo del width

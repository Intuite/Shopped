use shopped;
select * from unit;
set @dr = (select id from unit where abbrev = "dr");
set @ds = (select id from unit where abbrev = "ds");
set @pn = (select id from unit where abbrev = "pn");
set @tsp = (select id from unit where abbrev = "tsp");
set @tbsp = (select id from unit where abbrev = "tbsp");
set @C = (select id from unit where abbrev = "C");
set @l = (select id from unit where abbrev = "l");
set @ml = (select id from unit where abbrev = "ml");
set @g = (select id from unit where abbrev = "g");
set @kg = (select id from unit where abbrev = "kg");
set @cm = (select id from unit where abbrev = "cm");
set @in = (select id from unit where abbrev = "in");

insert into ingredient (name, unit_id, status)
values
	("Vanilla",@dr,"ACTIVE"),
	("Allspice",@g,"ACTIVE"),
	("Almond",@g,"ACTIVE"),
	("All-purpose flour",@g,"ACTIVE"),
	("Amaretti",@g,"ACTIVE"),
	("Anchovy",@g,"ACTIVE"),
	("Anise",@g,"ACTIVE"),
	("Apple juice",@ml,"ACTIVE"),
	("Artichoke",@g,"ACTIVE"),
	("Artichoke heart",@g,"ACTIVE"),
	("Artichoke",@g,"ACTIVE"),
	("Asparagus",@g,"ACTIVE"),
	("Asparagus spear",@g,"ACTIVE"),
	("Aubergine",@g,"ACTIVE"),
	("Avocado",@g,"ACTIVE"),
	("Bacon",@kg,"ACTIVE"),
	("Baguette",@g,"ACTIVE"),
	("Baked beans",@g,"ACTIVE"),
	("Balsamic vinegar",@ml,"ACTIVE"),
	("Bamboo",@in,"ACTIVE"),
	("Banana",@g,"ACTIVE"),
	("Bap",@g,"ACTIVE"),
	("Basil",@g,"ACTIVE"),
	("Bay leaf",@g,"ACTIVE"),
	("Bay leaves",@g,"ACTIVE"),
	("Bean",@g,"ACTIVE"),
	("Beef",@kg,"ACTIVE"),
	("Beef mince",@kg,"ACTIVE"),
	("Beef brisket",@kg,"ACTIVE"),
	("Beef stock",@kg,"ACTIVE"),
	("Bell pepper",@g,"ACTIVE"),
	("Berry",@g,"ACTIVE"),
	("Berries",@g,"ACTIVE"),
	("Bicarbonate of soda",@g,"ACTIVE"),
	("Biscuit",@g,"ACTIVE"),
	("Biscuit",@g,"ACTIVE"),
	("Black olive",@g,"ACTIVE"),
	("Black pepper",@g,"ACTIVE"),
	("Black-eyed peas",@g,"ACTIVE"),
	("Blueberries",@g,"ACTIVE"),
	("Blueberry",@g,"ACTIVE"),
	("Bonnet chilli",@g,"ACTIVE"),
	("Bouillon",@g,"ACTIVE"),
	("Bourbon",@g,"ACTIVE"),
	("Braising steak",@g,"ACTIVE"),
	("Bran",@g,"ACTIVE"),
	("Brandy",@g,"ACTIVE"),
	("Bread",@g,"ACTIVE"),
	("Breadcrumbs",@g,"ACTIVE"),
	("Bbq sauce",@g,"ACTIVE"),
	("Habanero sauce",@g,"ACTIVE"),
	("Brie",@g,"ACTIVE"),
	("Brine",@g,"ACTIVE"),
	("Broccoli",@g,"ACTIVE"),
	("Brown rice",@g,"ACTIVE"),
	("Brown sauce",@g,"ACTIVE"),
	("Brown sugar",@g,"ACTIVE"),
	("Buckwheat",@g,"ACTIVE"),
	("Buffalo",@g,"ACTIVE"),
	("Bun",@g,"ACTIVE"),
	("Butter",@g,"ACTIVE"),
	("Buttermilk",@g,"ACTIVE"),
	("Butternut",@g,"ACTIVE"),
	("Butternut squash",@g,"ACTIVE"),
	("Butterscotch",@g,"ACTIVE"),
	("Cabbage",@g,"ACTIVE"),
	("Cake",@g,"ACTIVE"),
	("Candy",@g,"ACTIVE"),
	("Candies",@g,"ACTIVE"),
	("Cannellini",@g,"ACTIVE"),
	("Canola oil",@ml,"ACTIVE"),
	("Caper",@g,"ACTIVE"),
	("Caramel",@g,"ACTIVE"),
	("Caraway seed",@g,"ACTIVE"),
	("Cardamom",@g,"ACTIVE"),
	("Carrot",@g,"ACTIVE"),
	("Carrot",@g,"ACTIVE"),
	("Cashew",@g,"ACTIVE"),
	("Caster sugar",@g,"ACTIVE"),
	("Cayenne pepper",@g,"ACTIVE"),
	("Celeriac",@g,"ACTIVE"),
	("Celery",@g,"ACTIVE"),
	("Cereal",@g,"ACTIVE"),
	("Champagne",@g,"ACTIVE"),
	("Chard",@g,"ACTIVE"),
	("Cheddar cheese",@g,"ACTIVE"),
	("Cheese",@g,"ACTIVE"),
	("Cherries",@g,"ACTIVE"),
	("Cherry",@g,"ACTIVE"),
	("Cherry tomatoe",@g,"ACTIVE"),
	("Chestnut",@g,"ACTIVE"),
	("Chicken",@g,"ACTIVE"),
	("Chicken breast",@g,"ACTIVE"),
	("Chicken drumstick",@g,"ACTIVE"),
	("Chicken stock",@g,"ACTIVE"),
	("Chicken thigh",@g,"ACTIVE"),
	("Chicken leg",@g,"ACTIVE"),
	("Chicken",@g,"ACTIVE"),
	("Chickpea",@g,"ACTIVE"),
	("Chicory",@g,"ACTIVE"),
	("Chile",@g,"ACTIVE"),
	("Chilli",@g,"ACTIVE"),
	("Chillies",@g,"ACTIVE"),
	("Chipotle",@g,"ACTIVE"),
	("Chip",@g,"ACTIVE"),
	("Chive",@g,"ACTIVE"),
	("Chive",@g,"ACTIVE"),
	("Chocolate",@g,"ACTIVE"),
	("Chop",@g,"ACTIVE"),
	("Choy",@g,"ACTIVE"),
	("Chutney",@g,"ACTIVE"),
	("Ciabatta",@g,"ACTIVE"),
	("Cider",@g,"ACTIVE"),
	("Cinnamon",@g,"ACTIVE"),
	("Clam",@g,"ACTIVE"),
	("Clarified butter",@g,"ACTIVE"),
	("Clove",@g,"ACTIVE"),
	("Cocoa",@g,"ACTIVE"),
	("Coconut",@g,"ACTIVE"),
	("Orange essence",@ml,"ACTIVE"),
	("Almond essence",@ml,"ACTIVE"),
	("Coconut milk",@l,"ACTIVE"),
	("Cod",@g,"ACTIVE"),
	("Coffee",@l,"ACTIVE"),
	("Coleslaw",@g,"ACTIVE"),
	("Condensed milk",@g,"ACTIVE"),
	("Coriander",@g,"ACTIVE"),
	("Coriander leaves",@g,"ACTIVE"),
	("Corn",@g,"ACTIVE"),
	("Cornflour",@g,"ACTIVE"),
	("Cornmeal",@g,"ACTIVE"),
	("Courgette",@g,"ACTIVE"),
	("Couscous",@g,"ACTIVE"),
	("Cranberries",@g,"ACTIVE"),
	("Cranberry",@g,"ACTIVE"),
	("Cranberry juice",@ml,"ACTIVE"),
	("Cream",@g,"ACTIVE"),
	("Single cream",@g,"ACTIVE"),
	("Double cream",@g,"ACTIVE"),
	("Sour cream",@g,"ACTIVE"),
	("Crﾏme fraiche",@g,"ACTIVE"),
	("Crisp",@g,"ACTIVE"),
	("Crust",@g,"ACTIVE"),
	("Creme de cassis",@g,"ACTIVE"),
	("Cucumber",@g,"ACTIVE"),
	("Cumin",@g,"ACTIVE"),
	("Custard",@g,"ACTIVE"),
	("Dijon mustard",@g,"ACTIVE"),
	("Dill",@g,"ACTIVE"),
	("Duck",@kg,"ACTIVE"),
	("Edamame bean",@g,"ACTIVE"),
	("Egg",@g,"ACTIVE"),
	("Espresso",@g,"ACTIVE"),
	("Fennel",@g,"ACTIVE"),
	("Feta",@g,"ACTIVE"),
	("Fig",@g,"ACTIVE"),
	("Fillet steak",@g,"ACTIVE"),
	("Filo pastry",@g,"ACTIVE"),
	("Fish",@g,"ACTIVE"),
	("Flank",@g,"ACTIVE"),
	("Flour",@g,"ACTIVE"),
	("Focaccia",@g,"ACTIVE"),
	("Foie gras",@g,"ACTIVE"),
	("Fruit",@g,"ACTIVE"),
	("Fudge",@g,"ACTIVE"),
	("Garam masala",@g,"ACTIVE"),
	("Garlic",@g,"ACTIVE"),
	("Gelatine",@g,"ACTIVE"),
	("Gin",@g,"ACTIVE"),
	("Ginger",@g,"ACTIVE"),
	("Glucose",@g,"ACTIVE"),
	("Goat",@g,"ACTIVE"),
	("Goat's cheese",@g,"ACTIVE"),
	("Golden syrup",@g,"ACTIVE"),
	("Gorgonzola",@g,"ACTIVE"),
	("Gouda",@g,"ACTIVE"),
	("Grape",@g,"ACTIVE"),
	("Grapefruit",@g,"ACTIVE"),
	("Grape",@g,"ACTIVE"),
	("Grapeseed",@g,"ACTIVE"),
	("Green olive",@g,"ACTIVE"),
	("Green pepper",@g,"ACTIVE"),
	("Green",@g,"ACTIVE"),
	("Gruyere",@g,"ACTIVE"),
	("Gruyere cheese",@g,"ACTIVE"),
	("Haddock",@g,"ACTIVE"),
	("Ham",@g,"ACTIVE"),
	("Harissa",@g,"ACTIVE"),
	("Herbs",@g,"ACTIVE"),
	("Hoisin",@g,"ACTIVE"),
	("Hoisin sauce",@g,"ACTIVE"),
	("Honey",@g,"ACTIVE"),
	("Honeydew melon",@g,"ACTIVE"),
	("Horseradish",@g,"ACTIVE"),
	("Iceberg lettuce",@g,"ACTIVE"),
	("Icing sugar",@g,"ACTIVE"),
	("Jalapeno pepper",@g,"ACTIVE"),
	("Juice",@ml,"ACTIVE"),
	("Julienne",@g,"ACTIVE"),
	("Kalamata olives",@g,"ACTIVE"),
	("Ketchup",@g,"ACTIVE"),
	("Kidney bean",@g,"ACTIVE"),
	("Lager",@g,"ACTIVE"),
	("Lamb",@g,"ACTIVE"),
	("Lard",@g,"ACTIVE"),
	("Lardons",@g,"ACTIVE"),
	("Lasagne",@g,"ACTIVE"),
	("Lavender",@g,"ACTIVE"),
	("Leek",@g,"ACTIVE"),
	("Lemon",@g,"ACTIVE"),
	("Lemongrass",@g,"ACTIVE"),
	("Lentil",@g,"ACTIVE"),
	("Lettuce",@g,"ACTIVE"),
	("Lime",@g,"ACTIVE"),
	("Limoncello",@g,"ACTIVE"),
	("Lobster",@g,"ACTIVE"),
	("Loin",@g,"ACTIVE"),
	("Lychee",@g,"ACTIVE"),
	("Macadamia nut",@g,"ACTIVE"),
	("Malt",@g,"ACTIVE"),
	("Mangetout",@g,"ACTIVE"),
	("Mango",@g,"ACTIVE"),
	("Mangoes",@g,"ACTIVE"),
	("Maple syrup",@g,"ACTIVE"),
	("Marinara",@g,"ACTIVE"),
	("Marshmallow",@g,"ACTIVE"),
	("Marzano",@g,"ACTIVE"),
	("Mascarpone",@g,"ACTIVE"),
	("Mayonnaise",@g,"ACTIVE"),
	("Meat",@g,"ACTIVE"),
	("Melon",@g,"ACTIVE"),
	("Milk",@g,"ACTIVE"),
	("Mince",@g,"ACTIVE"),
	("Mint",@g,"ACTIVE"),
	("Miso soup",@g,"ACTIVE"),
	("Molasses",@g,"ACTIVE"),
	("Monterey jack",@g,"ACTIVE"),
	("Mozzarella",@g,"ACTIVE"),
	("Mushroom",@g,"ACTIVE"),
	("Mustard",@g,"ACTIVE"),
	("New potato",@g,"ACTIVE"),
	("Noodle",@g,"ACTIVE"),
	("Nutella",@g,"ACTIVE"),
	("Nutmeg",@g,"ACTIVE"),
	("Nut",@g,"ACTIVE"),
	("Oat",@g,"ACTIVE"),
	("Oil",@ml,"ACTIVE"),
	("Okra",@g,"ACTIVE"),
	("Olive oil",@ml,"ACTIVE"),
	("Olive",@g,"ACTIVE"),
	("Onion",@g,"ACTIVE"),
	("Onion ring",@g,"ACTIVE"),
	("Orange",@g,"ACTIVE"),
	("Orange juice",@ml,"ACTIVE"),
	("Oregano",@g,"ACTIVE"),
	("Oyster",@g,"ACTIVE"),
	("Pak choi",@g,"ACTIVE"),
	("Pancetta",@g,"ACTIVE"),
	("Panko",@g,"ACTIVE"),
	("Papaya",@g,"ACTIVE"),
	("Paprika",@g,"ACTIVE"),
	("Parma ham",@g,"ACTIVE"),
	("Parmesan",@g,"ACTIVE"),
	("Parmigiano",@g,"ACTIVE"),
	("Parmigiano-reggiano",@g,"ACTIVE"),
	("Parsley",@g,"ACTIVE"),
	("Parsnip",@g,"ACTIVE"),
	("Passion fruit",@g,"ACTIVE"),
	("Pasta",@g,"ACTIVE"),
	("Pasta shell",@g,"ACTIVE"),
	("Pastry",@g,"ACTIVE"),
	("Pea",@g,"ACTIVE"),
	("Peach",@g,"ACTIVE"),
	("Peaches",@g,"ACTIVE"),
	("Peanut",@g,"ACTIVE"),
	("Peanut",@g,"ACTIVE"),
	("Pear",@g,"ACTIVE"),
	("Pea",@g,"ACTIVE"),
	("Pecan",@g,"ACTIVE"),
	("Pecorino",@g,"ACTIVE"),
	("Penne",@g,"ACTIVE"),
	("Penne pasta",@g,"ACTIVE"),
	("Pepper",@g,"ACTIVE"),
	("Peppercorn",@g,"ACTIVE"),
	("Peppers",@g,"ACTIVE"),
	("Pesto",@g,"ACTIVE"),
	("Pickle",@g,"ACTIVE"),
	("Pickled gherkin",@g,"ACTIVE"),
	("Pickled onion",@g,"ACTIVE"),
	("Pickled red onion",@g,"ACTIVE"),
	("Pie",@g,"ACTIVE"),
	("Pimento pepper",@g,"ACTIVE"),
	("Pine nut",@g,"ACTIVE"),
	("Pineapple",@g,"ACTIVE"),
	("Pistachio",@g,"ACTIVE"),
	("Pita",@g,"ACTIVE"),
	("Pitta",@g,"ACTIVE"),
	("Pitted olive",@g,"ACTIVE"),
	("Pizza",@g,"ACTIVE"),
	("Plantain",@g,"ACTIVE"),
	("Plum",@g,"ACTIVE"),
	("Plum tomato",@g,"ACTIVE"),
	("Plum tomatoes",@g,"ACTIVE"),
	("Sun dried tomato",@g,"ACTIVE"),
	("Poblano",@g,"ACTIVE"),
	("Pod",@g,"ACTIVE"),
	("Polenta",@g,"ACTIVE"),
	("Pomegranate",@g,"ACTIVE"),
	("Popcorn",@g,"ACTIVE"),
	("Pork",@g,"ACTIVE"),
	("Pork chop",@g,"ACTIVE"),
	("Porridge",@g,"ACTIVE"),
	("Potato",@g,"ACTIVE"),
	("Potatoes",@g,"ACTIVE"),
	("Prawn",@g,"ACTIVE"),
	("Prosciutto",@g,"ACTIVE"),
	("Prosciutto ham",@g,"ACTIVE"),
	("Provolone",@g,"ACTIVE"),
	("Pumpkin",@g,"ACTIVE"),
	("Quail",@g,"ACTIVE"),
	("Radicchio",@g,"ACTIVE"),
	("Radish",@g,"ACTIVE"),
	("Radishes",@g,"ACTIVE"),
	("Rapeseed oil",@ml,"ACTIVE"),
	("Raspberries",@g,"ACTIVE"),
	("Raspberry",@g,"ACTIVE"),
	("Red pepper",@g,"ACTIVE"),
	("Red onion",@g,"ACTIVE"),
	("Relish",@g,"ACTIVE"),
	("Rhubarb",@g,"ACTIVE"),
	("Rib",@g,"ACTIVE"),
	("Rice",@g,"ACTIVE"),
	("Ricotta",@g,"ACTIVE"),
	("Rocket",@g,"ACTIVE"),
	("Roll",@g,"ACTIVE"),
	("Rose",@g,"ACTIVE"),
	("Rosemary",@g,"ACTIVE"),
	("Russet",@g,"ACTIVE"),
	("Saffron",@g,"ACTIVE"),
	("Sage",@g,"ACTIVE"),
	("Sake",@g,"ACTIVE"),
	("Salad",@g,"ACTIVE"),
	("Salami",@g,"ACTIVE"),
	("Salmon",@g,"ACTIVE"),
	("Salmon flake",@g,"ACTIVE"),
	("Salsa",@g,"ACTIVE"),
	("Salt",@g,"ACTIVE"),
	("Sauce",@g,"ACTIVE"),
	("Sausage",@g,"ACTIVE"),
	("Savoy cabbage",@g,"ACTIVE"),
	("Scallop",@g,"ACTIVE"),
	("Schnapps",@g,"ACTIVE"),
	("Self raising flour",@g,"ACTIVE"),
	("Semolina",@g,"ACTIVE"),
	("Serrano ham",@g,"ACTIVE"),
	("Shallot",@g,"ACTIVE"),
	("Shaohsing",@g,"ACTIVE"),
	("Sherry",@g,"ACTIVE"),
	("Shiitake",@g,"ACTIVE"),
	("Shiitake mushroom",@g,"ACTIVE"),
	("Shortcrust pastry",@g,"ACTIVE"),
	("Sirloin",@g,"ACTIVE"),
	("Sirloin steak",@g,"ACTIVE"),
	("Slaw",@g,"ACTIVE"),
	("Smoky bacon",@g,"ACTIVE"),
	("Snapper",@g,"ACTIVE"),
	("Sourdough",@g,"ACTIVE"),
	("Soy sauce",@g,"ACTIVE"),
	("Spaghetti",@g,"ACTIVE"),
	("Spice",@g,"ACTIVE"),
	("Spinach",@g,"ACTIVE"),
	("Sponge",@g,"ACTIVE"),
	("Spring onion",@g,"ACTIVE"),
	("Sprout",@g,"ACTIVE"),
	("Squash",@g,"ACTIVE"),
	("Squid",@g,"ACTIVE"),
	("Sriracha",@g,"ACTIVE"),
	("Steak",@g,"ACTIVE"),
	("Stock",@g,"ACTIVE"),
	("Stout",@g,"ACTIVE"),
	("Strawberries",@g,"ACTIVE"),
	("Strawberry",@g,"ACTIVE"),
	("Stuffing",@g,"ACTIVE"),
	("Sugar",@g,"ACTIVE"),
	("Sultana",@g,"ACTIVE"),
	("Sunflower oil",@ml,"ACTIVE"),
	("Sweet pepper",@g,"ACTIVE"),
	("Sweetcorn",@g,"ACTIVE"),
	("Sweets",@g,"ACTIVE"),
	("Swordfish",@g,"ACTIVE"),
	("Tangerine",@g,"ACTIVE"),
	("Tapioca",@g,"ACTIVE"),
	("Tarragon",@g,"ACTIVE"),
	("Tartar",@g,"ACTIVE"),
	("Tea",@g,"ACTIVE"),
	("Tequila",@g,"ACTIVE"),
	("Thyme",@g,"ACTIVE"),
	("Toast",@g,"ACTIVE"),
	("Toffee",@g,"ACTIVE"),
	("Tofu",@g,"ACTIVE"),
	("Tomatillos",@g,"ACTIVE"),
	("Tomato",@g,"ACTIVE"),
	("Tomato puree",@g,"ACTIVE"),
	("Tomatoes",@g,"ACTIVE"),
	("Tortilla",@g,"ACTIVE"),
	("Treacle",@g,"ACTIVE"),
	("Tuna steak",@g,"ACTIVE"),
	("Tuna",@g,"ACTIVE"),
	("Turkey",@g,"ACTIVE"),
	("Turkey breast",@g,"ACTIVE"),
	("Turkey thigh",@g,"ACTIVE"),
	("Turmeric",@g,"ACTIVE"),
	("Turnip",@g,"ACTIVE"),
	("Vanilla",@g,"ACTIVE"),
	("Vanilla essence",@dr,"ACTIVE"),
	("Vanilla pod",@dr,"ACTIVE"),
	("Veal",@g,"ACTIVE"),
	("Vegetable oil",@ml,"ACTIVE"),
	("Vermouth",@g,"ACTIVE"),
	("Vinaigrette",@ml,"ACTIVE"),
	("Vinegar",@ml,"ACTIVE"),
	("White wine vinegar",@ml,"ACTIVE"),
	("Red wine vinegar",@ml,"ACTIVE"),
	("Vodka",@l,"ACTIVE"),
	("Waffle",@g,"ACTIVE"),
	("Walnut",@g,"ACTIVE"),
	("Watercress",@g,"ACTIVE"),
	("Watermelon",@g,"ACTIVE"),
	("White rice",@g,"ACTIVE"),
	("Wholegrain pasta",@g,"ACTIVE"),
	("Wine",@g,"ACTIVE"),
	("Worcestershire sauce",@g,"ACTIVE"),
	("Yeast",@g,"ACTIVE"),
	("Yellow pepper",@g,"ACTIVE"),
	("Greek yoghurt",@ml,"ACTIVE"),
	("Natural yoghurt",@ml,"ACTIVE"),
	("Yoghurt",@ml,"ACTIVE"),
	("Yogurt",@ml,"ACTIVE"),
	("Red onion",@g,"ACTIVE");

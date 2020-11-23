# This must be ran only the first time the database is created
use shopped;

# INITIALIZE TAG TYPES
insert into tag_type (name, description, status)
values ('culture', 'Mauris lacinia sapien quis libero.', 'INACTIVE'),
       ('bitter',
        'Morbi ut odio. Cras mi pede, malesuada in, imperdiet et, commodo vulputate, justo. In blandit ultrices enim.',
        'ACTIVE'),
        ('country',
        'Morbi ut odio. Cras mi pede, malesuada in, imperdiet et, commodo vulputate, justo. In blandit ultrices enim.',
        'ACTIVE'),
       ('sweet',
        'Curabitur gravida nisi at nibh. In hac habitasse platea dictumst. Aliquam augue quam, sollicitudin vitae, consectetuer eget, rutrum at, lorem. Integer tincidunt ante vel ipsum.',
        'ACTIVE'),
       ('salty', 'Vestibulum sed magna at nunc commodo placerat.', 'ACTIVE'),
       ('professional', 'Morbi a ipsum. Integer a nibh. In quis justo.', 'INACTIVE'),
       ('sour',
        'Nulla suscipit ligula in lacus. Curabitur at ipsum ac tellus semper interdum. Mauris ullamcorper purus sit amet nulla. Quisque arcu libero, rutrum ac, lobortis vel, dapibus at, diam.',
        'ACTIVE'),
       ('spicy', 'Morbi non quam nec dui luctus rutrum. Nulla tellus.', 'ACTIVE');

# INITIALIZE RECIPE TAGS
insert into recipe_tag (name, description, status, type_id)
values ('Domainer',
        'Nulla nisl. Nunc nisl. Duis bibendum, felis sed interdum venenatis, turpis enim blandit mi, in porttitor pede justo eu massa. Donec dapibus.',
        'ACTIVE', 6),
       ('Sub-Ex', 'Fusce lacus purus, aliquet at, feugiat non, pretium quis, lectus.', 'INACTIVE', 5),
       ('Fix San', 'Duis mattis egestas metus. Aenean fermentum.', 'ACTIVE', 5),
       ('Mat Lam Tam',
        'Suspendisse potenti. Cras in purus eu magna vulputate luctus. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Vivamus vestibulum sagittis sapien.',
        'INACTIVE', 4),
       ('Fix San', 'Suspendisse potenti.', 'INACTIVE', 4),
       ('Namfix', 'Vivamus tortor.', 'ACTIVE', 5),
       ('Zamit', 'Morbi quis tortor id nulla ultrices aliquet.', 'ACTIVE', 1),
       ('Lotstring', 'Cras in purus eu magna vulputate luctus.', 'INACTIVE', 4),
       ('Cardguard', 'Aliquam erat volutpat.', 'INACTIVE', 2),
       ('Ventosanzap', 'Phasellus sit amet erat.', 'ACTIVE', 4),
       ('Transcof', 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Proin risus. Praesent lectus.', 'ACTIVE',
        5),
       ('Regrant',
        'Sed vel enim sit amet nunc viverra dapibus. Nulla suscipit ligula in lacus. Curabitur at ipsum ac tellus semper interdum.',
        'INACTIVE', 2),
       ('Kanlam',
        'Vivamus vel nulla eget eros elementum pellentesque. Quisque porta volutpat erat. Quisque erat eros, viverra eget, congue eget, semper rutrum, nulla.',
        'ACTIVE', 5),
       ('Sonair',
        'Duis mattis egestas metus. Aenean fermentum. Donec ut mauris eget massa tempor convallis. Nulla neque libero, convallis eget, eleifend luctus, ultricies eu, nibh.',
        'ACTIVE', 3),
       ('Asoka',
        'Aliquam sit amet diam in magna bibendum imperdiet. Nullam orci pede, venenatis non, sodales sed, tincidunt eu, felis. Fusce posuere felis sed lacus.',
        'INACTIVE', 4),
       ('Zontrax', 'Nulla nisl. Nunc nisl.', 'INACTIVE', 3),
       ('Home Ing', 'Cras non velit nec nisi vulputate nonummy.', 'ACTIVE', 5),
       ('Home Ing', 'Nulla ut erat id mauris vulputate elementum.', 'INACTIVE', 4),
       ('Fixflex',
        'Duis bibendum, felis sed interdum venenatis, turpis enim blandit mi, in porttitor pede justo eu massa. Donec dapibus. Duis at velit eu est congue elementum. In hac habitasse platea dictumst.',
        'INACTIVE', 1),
       ('Transcof', 'Fusce lacus purus, aliquet at, feugiat non, pretium quis, lectus. Suspendisse potenti.',
        'INACTIVE', 6),
       ('Vagram',
        'Vestibulum ac est lacinia nisi venenatis tristique. Fusce congue, diam id ornare imperdiet, sapien urna pretium nisl, ut volutpat sapien arcu sed augue.',
        'ACTIVE', 6),
       ('Hatity', 'Suspendisse accumsan tortor quis turpis.', 'ACTIVE', 3),
       ('Zathin', 'Suspendisse potenti.', 'ACTIVE', 5),
       ('Sonsing',
        'Mauris enim leo, rhoncus sed, vestibulum sit amet, cursus id, turpis. Integer aliquet, massa id lobortis convallis, tortor risus dapibus augue, vel accumsan tellus nisi eu orci. Mauris lacinia sapien quis libero.',
        'INACTIVE', 3),
       ('Asoka', 'Praesent blandit lacinia erat. Vestibulum sed magna at nunc commodo placerat.', 'ACTIVE', 3),
       ('Sub-Ex', 'Nulla tellus. In sagittis dui vel nisl. Duis ac nibh.', 'ACTIVE', 3),
       ('Bamity', 'Donec dapibus. Duis at velit eu est congue elementum. In hac habitasse platea dictumst.', 'INACTIVE',
        1),
       ('Greenlam', 'Vestibulum rutrum rutrum neque. Aenean auctor gravida sem.', 'INACTIVE', 3),
       ('Bamity',
        'Cras mi pede, malesuada in, imperdiet et, commodo vulputate, justo. In blandit ultrices enim. Lorem ipsum dolor sit amet, consectetuer adipiscing elit.',
        'ACTIVE', 1);

# INITIALIZE UNITS
insert into unit (name, abbrev)
values ('drop', 'dr'),        # 1
       ('dash', 'ds'),        # 2
       ('pinch', 'pn'),       # 3
       ('teaspoon', 'tsp'),   # 4
       ('cup', 'C'),          # 5
       ('liters', 'l'),       # 6
       ('milliliter', 'ml'),  # 7
       ('grams', 'g'),        # 8
       ('kilograms', 'kg'),   # 9
       ('centimeters', 'cm'), # 10
       ('inches', 'in');

# INITIALIZE INGREDIENTS
insert into ingredient (name, unit_id, status)
values ('Vanilla', 1, 'ACTIVE'),
       ('Allspice', 8, 'ACTIVE'),
       ('Almond', 8, 'ACTIVE'),
       ('All-purpose flour', 8, 'ACTIVE'),
       ('Amaretti', 8, 'ACTIVE'),
       ('Anchovy', 8, 'ACTIVE'),
       ('Anise', 8, 'ACTIVE'),
       ('Apple juice', 8, 'ACTIVE'),
       ('Artichoke', 8, 'ACTIVE'),
       ('Artichoke heart', 8, 'ACTIVE'),
       ('Artichoke', 8, 'ACTIVE'),
       ('Asparagus', 8, 'ACTIVE'),
       ('Asparagus spear', 8, 'ACTIVE'),
       ('Aubergine', 8, 'ACTIVE'),
       ('Avocado', 8, 'ACTIVE'),
       ('Bacon', 9, 'ACTIVE'),
       ('Baguette', 8, 'ACTIVE'),
       ('Baked beans', 8, 'ACTIVE'),
       ('Balsamic vinegar', 8, 'ACTIVE'),
       ('Bamboo', 11, 'ACTIVE'),
       ('Banana', 8, 'ACTIVE'),
       ('Bap', 8, 'ACTIVE'),
       ('Basil', 8, 'ACTIVE'),
       ('Bay leaf', 8, 'ACTIVE'),
       ('Bay leaves', 8, 'ACTIVE'),
       ('Bean', 8, 'ACTIVE'),
       ('Beef', 9, 'ACTIVE'),
       ('Beef mince', 9, 'ACTIVE'),
       ('Beef brisket', 9, 'ACTIVE'),
       ('Beef stock', 9, 'ACTIVE'),
       ('Bell pepper', 8, 'ACTIVE'),
       ('Berry', 8, 'ACTIVE'),
       ('Berries', 8, 'ACTIVE'),
       ('Bicarbonate of soda', 8, 'ACTIVE'),
       ('Biscuit', 8, 'ACTIVE'),
       ('Biscuit', 8, 'ACTIVE'),
       ('Black olive', 8, 'ACTIVE'),
       ('Black pepper', 8, 'ACTIVE'),
       ('Black-eyed peas', 8, 'ACTIVE'),
       ('Blueberries', 8, 'ACTIVE'),
       ('Blueberry', 8, 'ACTIVE'),
       ('Bonnet chilli', 8, 'ACTIVE'),
       ('Bouillon', 8, 'ACTIVE'),
       ('Bourbon', 8, 'ACTIVE'),
       ('Braising steak', 8, 'ACTIVE'),
       ('Bran', 8, 'ACTIVE'),
       ('Brandy', 8, 'ACTIVE'),
       ('Bread', 8, 'ACTIVE'),
       ('Breadcrumbs', 8, 'ACTIVE'),
       ('Bbq sauce', 8, 'ACTIVE'),
       ('Habanero sauce', 8, 'ACTIVE'),
       ('Brie', 8, 'ACTIVE'),
       ('Brine', 8, 'ACTIVE'),
       ('Broccoli', 8, 'ACTIVE'),
       ('Brown rice', 8, 'ACTIVE'),
       ('Brown sauce', 8, 'ACTIVE'),
       ('Brown sugar', 8, 'ACTIVE'),
       ('Buckwheat', 8, 'ACTIVE'),
       ('Buffalo', 8, 'ACTIVE'),
       ('Bun', 8, 'ACTIVE'),
       ('Butter', 8, 'ACTIVE'),
       ('Buttermilk', 8, 'ACTIVE'),
       ('Butternut', 8, 'ACTIVE'),
       ('Butternut squash', 8, 'ACTIVE'),
       ('Butterscotch', 8, 'ACTIVE'),
       ('Cabbage', 8, 'ACTIVE'),
       ('Cake', 8, 'ACTIVE'),
       ('Candy', 8, 'ACTIVE'),
       ('Candies', 8, 'ACTIVE'),
       ('Cannellini', 8, 'ACTIVE'),
       ('Canola oil', 8, 'ACTIVE'),
       ('Caper', 8, 'ACTIVE'),
       ('Caramel', 8, 'ACTIVE'),
       ('Caraway seed', 8, 'ACTIVE'),
       ('Cardamom', 8, 'ACTIVE'),
       ('Carrot', 8, 'ACTIVE'),
       ('Carrot', 8, 'ACTIVE'),
       ('Cashew', 8, 'ACTIVE'),
       ('Caster sugar', 8, 'ACTIVE'),
       ('Cayenne pepper', 8, 'ACTIVE'),
       ('Celeriac', 8, 'ACTIVE'),
       ('Celery', 8, 'ACTIVE'),
       ('Cereal', 8, 'ACTIVE'),
       ('Champagne', 8, 'ACTIVE'),
       ('Chard', 8, 'ACTIVE'),
       ('Cheddar cheese', 8, 'ACTIVE'),
       ('Cheese', 8, 'ACTIVE'),
       ('Cherries', 8, 'ACTIVE'),
       ('Cherry', 8, 'ACTIVE'),
       ('Cherry tomatoe', 8, 'ACTIVE'),
       ('Chestnut', 8, 'ACTIVE'),
       ('Chicken', 8, 'ACTIVE'),
       ('Chicken breast', 8, 'ACTIVE'),
       ('Chicken drumstick', 8, 'ACTIVE'),
       ('Chicken stock', 8, 'ACTIVE'),
       ('Chicken thigh', 8, 'ACTIVE'),
       ('Chicken leg', 8, 'ACTIVE'),
       ('Chicken', 8, 'ACTIVE'),
       ('Chickpea', 8, 'ACTIVE'),
       ('Chicory', 8, 'ACTIVE'),
       ('Chile', 8, 'ACTIVE'),
       ('Chilli', 8, 'ACTIVE'),
       ('Chillies', 8, 'ACTIVE'),
       ('Chipotle', 8, 'ACTIVE'),
       ('Chip', 8, 'ACTIVE'),
       ('Chive', 8, 'ACTIVE'),
       ('Chive', 8, 'ACTIVE'),
       ('Chocolate', 8, 'ACTIVE'),
       ('Chop', 8, 'ACTIVE'),
       ('Choy', 8, 'ACTIVE'),
       ('Chutney', 8, 'ACTIVE'),
       ('Ciabatta', 8, 'ACTIVE'),
       ('Cider', 8, 'ACTIVE'),
       ('Cinnamon', 8, 'ACTIVE'),
       ('Clam', 8, 'ACTIVE'),
       ('Clarified butter', 8, 'ACTIVE'),
       ('Clove', 8, 'ACTIVE'),
       ('Cocoa', 8, 'ACTIVE'),
       ('Coconut', 8, 'ACTIVE'),
       ('Orange essence', 8, 'ACTIVE'),
       ('Almond essence', 8, 'ACTIVE'),
       ('Coconut milk', 6, 'ACTIVE'),
       ('Cod', 8, 'ACTIVE'),
       ('Coffee', 6, 'ACTIVE'),
       ('Coleslaw', 8, 'ACTIVE'),
       ('Condensed milk', 8, 'ACTIVE'),
       ('Coriander', 8, 'ACTIVE'),
       ('Coriander leaves', 8, 'ACTIVE'),
       ('Corn', 8, 'ACTIVE'),
       ('Cornflour', 8, 'ACTIVE'),
       ('Cornmeal', 8, 'ACTIVE'),
       ('Courgette', 8, 'ACTIVE'),
       ('Couscous', 8, 'ACTIVE'),
       ('Cranberries', 8, 'ACTIVE'),
       ('Cranberry', 8, 'ACTIVE'),
       ('Cranberry juice', 8, 'ACTIVE'),
       ('Cream', 8, 'ACTIVE'),
       ('Single cream', 8, 'ACTIVE'),
       ('Double cream', 8, 'ACTIVE'),
       ('Sour cream', 8, 'ACTIVE'),
       ('Crﾏme fraiche', 8, 'ACTIVE'),
       ('Crisp', 8, 'ACTIVE'),
       ('Crust', 8, 'ACTIVE'),
       ('Creme de cassis', 8, 'ACTIVE'),
       ('Cucumber', 8, 'ACTIVE'),
       ('Cumin', 8, 'ACTIVE'),
       ('Custard', 8, 'ACTIVE'),
       ('Dijon mustard', 8, 'ACTIVE'),
       ('Dill', 8, 'ACTIVE'),
       ('Duck', 9, 'ACTIVE'),
       ('Edamame bean', 8, 'ACTIVE'),
       ('Egg', 8, 'ACTIVE'),
       ('Espresso', 8, 'ACTIVE'),
       ('Fennel', 8, 'ACTIVE'),
       ('Feta', 8, 'ACTIVE'),
       ('Fig', 8, 'ACTIVE'),
       ('Fillet steak', 8, 'ACTIVE'),
       ('Filo pastry', 8, 'ACTIVE'),
       ('Fish', 8, 'ACTIVE'),
       ('Flank', 8, 'ACTIVE'),
       ('Flour', 8, 'ACTIVE'),
       ('Focaccia', 8, 'ACTIVE'),
       ('Foie gras', 8, 'ACTIVE'),
       ('Fruit', 8, 'ACTIVE'),
       ('Fudge', 8, 'ACTIVE'),
       ('Garam masala', 8, 'ACTIVE'),
       ('Garlic', 8, 'ACTIVE'),
       ('Gelatine', 8, 'ACTIVE'),
       ('Gin', 8, 'ACTIVE'),
       ('Ginger', 8, 'ACTIVE'),
       ('Glucose', 8, 'ACTIVE'),
       ('Goat', 8, 'ACTIVE'),
       ('Goat\'s cheese', 8, 'ACTIVE'),
       ('Golden syrup', 8, 'ACTIVE'),
       ('Gorgonzola', 8, 'ACTIVE'),
       ('Gouda', 8, 'ACTIVE'),
       ('Grape', 8, 'ACTIVE'),
       ('Grapefruit', 8, 'ACTIVE'),
       ('Grape', 8, 'ACTIVE'),
       ('Grapeseed', 8, 'ACTIVE'),
       ('Green olive', 8, 'ACTIVE'),
       ('Green pepper', 8, 'ACTIVE'),
       ('Green', 8, 'ACTIVE'),
       ('Gruyere', 8, 'ACTIVE'),
       ('Gruyere cheese', 8, 'ACTIVE'),
       ('Haddock', 8, 'ACTIVE'),
       ('Ham', 8, 'ACTIVE'),
       ('Harissa', 8, 'ACTIVE'),
       ('Herbs', 8, 'ACTIVE'),
       ('Hoisin', 8, 'ACTIVE'),
       ('Hoisin sauce', 8, 'ACTIVE'),
       ('Honey', 8, 'ACTIVE'),
       ('Honeydew melon', 8, 'ACTIVE'),
       ('Horseradish', 8, 'ACTIVE'),
       ('Iceberg lettuce', 8, 'ACTIVE'),
       ('Icing sugar', 8, 'ACTIVE'),
       ('Jalapeno pepper', 8, 'ACTIVE'),
       ('Juice', 8, 'ACTIVE'),
       ('Julienne', 8, 'ACTIVE'),
       ('Kalamata olives', 8, 'ACTIVE'),
       ('Ketchup', 8, 'ACTIVE'),
       ('Kidney bean', 8, 'ACTIVE'),
       ('Lager', 8, 'ACTIVE'),
       ('Lamb', 8, 'ACTIVE'),
       ('Lard', 8, 'ACTIVE'),
       ('Lardons', 8, 'ACTIVE'),
       ('Lasagne', 8, 'ACTIVE'),
       ('Lavender', 8, 'ACTIVE'),
       ('Leek', 8, 'ACTIVE'),
       ('Lemon', 8, 'ACTIVE'),
       ('Lemongrass', 8, 'ACTIVE'),
       ('Lentil', 8, 'ACTIVE'),
       ('Lettuce', 8, 'ACTIVE'),
       ('Lime', 8, 'ACTIVE'),
       ('Limoncello', 8, 'ACTIVE'),
       ('Lobster', 8, 'ACTIVE'),
       ('Loin', 8, 'ACTIVE'),
       ('Lychee', 8, 'ACTIVE'),
       ('Macadamia nut', 8, 'ACTIVE'),
       ('Malt', 8, 'ACTIVE'),
       ('Mangetout', 8, 'ACTIVE'),
       ('Mango', 8, 'ACTIVE'),
       ('Mangoes', 8, 'ACTIVE'),
       ('Maple syrup', 8, 'ACTIVE'),
       ('Marinara', 8, 'ACTIVE'),
       ('Marshmallow', 8, 'ACTIVE'),
       ('Marzano', 8, 'ACTIVE'),
       ('Mascarpone', 8, 'ACTIVE'),
       ('Mayonnaise', 8, 'ACTIVE'),
       ('Meat', 8, 'ACTIVE'),
       ('Melon', 8, 'ACTIVE'),
       ('Milk', 8, 'ACTIVE'),
       ('Mince', 8, 'ACTIVE'),
       ('Mint', 8, 'ACTIVE'),
       ('Miso soup', 8, 'ACTIVE'),
       ('Molasses', 8, 'ACTIVE'),
       ('Monterey jack', 8, 'ACTIVE'),
       ('Mozzarella', 8, 'ACTIVE'),
       ('Mushroom', 8, 'ACTIVE'),
       ('Mustard', 8, 'ACTIVE'),
       ('New potato', 8, 'ACTIVE'),
       ('Noodle', 8, 'ACTIVE'),
       ('Nutella', 8, 'ACTIVE'),
       ('Nutmeg', 8, 'ACTIVE'),
       ('Nut', 8, 'ACTIVE'),
       ('Oat', 8, 'ACTIVE'),
       ('Oil', 8, 'ACTIVE'),
       ('Okra', 8, 'ACTIVE'),
       ('Olive oil', 8, 'ACTIVE'),
       ('Olive', 8, 'ACTIVE'),
       ('Onion', 8, 'ACTIVE'),
       ('Onion ring', 8, 'ACTIVE'),
       ('Orange', 8, 'ACTIVE'),
       ('Orange juice', 8, 'ACTIVE'),
       ('Oregano', 8, 'ACTIVE'),
       ('Oyster', 8, 'ACTIVE'),
       ('Pak choi', 8, 'ACTIVE'),
       ('Pancetta', 8, 'ACTIVE'),
       ('Panko', 8, 'ACTIVE'),
       ('Papaya', 8, 'ACTIVE'),
       ('Paprika', 8, 'ACTIVE'),
       ('Parma ham', 8, 'ACTIVE'),
       ('Parmesan', 8, 'ACTIVE'),
       ('Parmigiano', 8, 'ACTIVE'),
       ('Parmigiano-reggiano', 8, 'ACTIVE'),
       ('Parsley', 8, 'ACTIVE'),
       ('Parsnip', 8, 'ACTIVE'),
       ('Passion fruit', 8, 'ACTIVE'),
       ('Pasta', 8, 'ACTIVE'),
       ('Pasta shell', 8, 'ACTIVE'),
       ('Pastry', 8, 'ACTIVE'),
       ('Pea', 8, 'ACTIVE'),
       ('Peach', 8, 'ACTIVE'),
       ('Peaches', 8, 'ACTIVE'),
       ('Peanut', 8, 'ACTIVE'),
       ('Peanut', 8, 'ACTIVE'),
       ('Pear', 8, 'ACTIVE'),
       ('Pea', 8, 'ACTIVE'),
       ('Pecan', 8, 'ACTIVE'),
       ('Pecorino', 8, 'ACTIVE'),
       ('Penne', 8, 'ACTIVE'),
       ('Penne pasta', 8, 'ACTIVE'),
       ('Pepper', 8, 'ACTIVE'),
       ('Peppercorn', 8, 'ACTIVE'),
       ('Peppers', 8, 'ACTIVE'),
       ('Pesto', 8, 'ACTIVE'),
       ('Pickle', 8, 'ACTIVE'),
       ('Pickled gherkin', 8, 'ACTIVE'),
       ('Pickled onion', 8, 'ACTIVE'),
       ('Pickled red onion', 8, 'ACTIVE'),
       ('Pie', 8, 'ACTIVE'),
       ('Pimento pepper', 8, 'ACTIVE'),
       ('Pine nut', 8, 'ACTIVE'),
       ('Pineapple', 8, 'ACTIVE'),
       ('Pistachio', 8, 'ACTIVE'),
       ('Pita', 8, 'ACTIVE'),
       ('Pitta', 8, 'ACTIVE'),
       ('Pitted olive', 8, 'ACTIVE'),
       ('Pizza', 8, 'ACTIVE'),
       ('Plantain', 8, 'ACTIVE'),
       ('Plum', 8, 'ACTIVE'),
       ('Plum tomato', 8, 'ACTIVE'),
       ('Plum tomatoes', 8, 'ACTIVE'),
       ('Sun dried tomato', 8, 'ACTIVE'),
       ('Poblano', 8, 'ACTIVE'),
       ('Pod', 8, 'ACTIVE'),
       ('Polenta', 8, 'ACTIVE'),
       ('Pomegranate', 8, 'ACTIVE'),
       ('Popcorn', 8, 'ACTIVE'),
       ('Pork', 8, 'ACTIVE'),
       ('Pork chop', 8, 'ACTIVE'),
       ('Porridge', 8, 'ACTIVE'),
       ('Potato', 8, 'ACTIVE'),
       ('Potatoes', 8, 'ACTIVE'),
       ('Prawn', 8, 'ACTIVE'),
       ('Prosciutto', 8, 'ACTIVE'),
       ('Prosciutto ham', 8, 'ACTIVE'),
       ('Provolone', 8, 'ACTIVE'),
       ('Pumpkin', 8, 'ACTIVE'),
       ('Quail', 8, 'ACTIVE'),
       ('Radicchio', 8, 'ACTIVE'),
       ('Radish', 8, 'ACTIVE'),
       ('Radishes', 8, 'ACTIVE'),
       ('Rapeseed oil', 8, 'ACTIVE'),
       ('Raspberries', 8, 'ACTIVE'),
       ('Raspberry', 8, 'ACTIVE'),
       ('Red pepper', 8, 'ACTIVE'),
       ('Red onion', 8, 'ACTIVE'),
       ('Relish', 8, 'ACTIVE'),
       ('Rhubarb', 8, 'ACTIVE'),
       ('Rib', 8, 'ACTIVE'),
       ('Rice', 8, 'ACTIVE'),
       ('Ricotta', 8, 'ACTIVE'),
       ('Rocket', 8, 'ACTIVE'),
       ('Roll', 8, 'ACTIVE'),
       ('Rose', 8, 'ACTIVE'),
       ('Rosemary', 8, 'ACTIVE'),
       ('Russet', 8, 'ACTIVE'),
       ('Saffron', 8, 'ACTIVE'),
       ('Sage', 8, 'ACTIVE'),
       ('Sake', 8, 'ACTIVE'),
       ('Salad', 8, 'ACTIVE'),
       ('Salami', 8, 'ACTIVE'),
       ('Salmon', 8, 'ACTIVE'),
       ('Salmon flake', 8, 'ACTIVE'),
       ('Salsa', 8, 'ACTIVE'),
       ('Salt', 8, 'ACTIVE'),
       ('Sauce', 8, 'ACTIVE'),
       ('Sausage', 8, 'ACTIVE'),
       ('Savoy cabbage', 8, 'ACTIVE'),
       ('Scallop', 8, 'ACTIVE'),
       ('Schnapps', 8, 'ACTIVE'),
       ('Self raising flour', 8, 'ACTIVE'),
       ('Semolina', 8, 'ACTIVE'),
       ('Serrano ham', 8, 'ACTIVE'),
       ('Shallot', 8, 'ACTIVE'),
       ('Shaohsing', 8, 'ACTIVE'),
       ('Sherry', 8, 'ACTIVE'),
       ('Shiitake', 8, 'ACTIVE'),
       ('Shiitake mushroom', 8, 'ACTIVE'),
       ('Shortcrust pastry', 8, 'ACTIVE'),
       ('Sirloin', 8, 'ACTIVE'),
       ('Sirloin steak', 8, 'ACTIVE'),
       ('Slaw', 8, 'ACTIVE'),
       ('Smoky bacon', 8, 'ACTIVE'),
       ('Snapper', 8, 'ACTIVE'),
       ('Sourdough', 8, 'ACTIVE'),
       ('Soy sauce', 8, 'ACTIVE'),
       ('Spaghetti', 8, 'ACTIVE'),
       ('Spice', 8, 'ACTIVE'),
       ('Spinach', 8, 'ACTIVE'),
       ('Sponge', 8, 'ACTIVE'),
       ('Spring onion', 8, 'ACTIVE'),
       ('Sprout', 8, 'ACTIVE'),
       ('Squash', 8, 'ACTIVE'),
       ('Squid', 8, 'ACTIVE'),
       ('Sriracha', 8, 'ACTIVE'),
       ('Steak', 8, 'ACTIVE'),
       ('Stock', 8, 'ACTIVE'),
       ('Stout', 8, 'ACTIVE'),
       ('Strawberries', 8, 'ACTIVE'),
       ('Strawberry', 8, 'ACTIVE'),
       ('Stuffing', 8, 'ACTIVE'),
       ('Sugar', 8, 'ACTIVE'),
       ('Sultana', 8, 'ACTIVE'),
       ('Sunflower oil', 8, 'ACTIVE'),
       ('Sweet pepper', 8, 'ACTIVE'),
       ('Sweetcorn', 8, 'ACTIVE'),
       ('Sweets', 8, 'ACTIVE'),
       ('Swordfish', 8, 'ACTIVE'),
       ('Tangerine', 8, 'ACTIVE'),
       ('Tapioca', 8, 'ACTIVE'),
       ('Tarragon', 8, 'ACTIVE'),
       ('Tartar', 8, 'ACTIVE'),
       ('Tea', 8, 'ACTIVE'),
       ('Tequila', 8, 'ACTIVE'),
       ('Thyme', 8, 'ACTIVE'),
       ('Toast', 8, 'ACTIVE'),
       ('Toffee', 8, 'ACTIVE'),
       ('Tofu', 8, 'ACTIVE'),
       ('Tomatillos', 8, 'ACTIVE'),
       ('Tomato', 8, 'ACTIVE'),
       ('Tomato puree', 8, 'ACTIVE'),
       ('Tomatoes', 8, 'ACTIVE'),
       ('Tortilla', 8, 'ACTIVE'),
       ('Treacle', 8, 'ACTIVE'),
       ('Tuna steak', 8, 'ACTIVE'),
       ('Tuna', 8, 'ACTIVE'),
       ('Turkey', 8, 'ACTIVE'),
       ('Turkey breast', 8, 'ACTIVE'),
       ('Turkey thigh', 8, 'ACTIVE'),
       ('Turmeric', 8, 'ACTIVE'),
       ('Turnip', 8, 'ACTIVE'),
       ('Vanilla', 8, 'ACTIVE'),
       ('Vanilla essence', 1, 'ACTIVE'),
       ('Vanilla pod', 1, 'ACTIVE'),
       ('Veal', 8, 'ACTIVE'),
       ('Vegetable oil', 8, 'ACTIVE'),
       ('Vermouth', 8, 'ACTIVE'),
       ('Vinaigrette', 8, 'ACTIVE'),
       ('Vinegar', 8, 'ACTIVE'),
       ('White wine vinegar', 8, 'ACTIVE'),
       ('Red wine vinegar', 8, 'ACTIVE'),
       ('Vodka', 6, 'ACTIVE'),
       ('Waffle', 8, 'ACTIVE'),
       ('Walnut', 8, 'ACTIVE'),
       ('Watercress', 8, 'ACTIVE'),
       ('Watermelon', 8, 'ACTIVE'),
       ('White rice', 8, 'ACTIVE'),
       ('Wholegrain pasta', 8, 'ACTIVE'),
       ('Wine', 8, 'ACTIVE'),
       ('Worcestershire sauce', 8, 'ACTIVE'),
       ('Yeast', 8, 'ACTIVE'),
       ('Yellow pepper', 8, 'ACTIVE'),
       ('Greek yoghurt', 8, 'ACTIVE'),
       ('Natural yoghurt', 8, 'ACTIVE'),
       ('Yoghurt', 8, 'ACTIVE'),
       ('Yogurt', 8, 'ACTIVE'),
       ('Red onion', 8, 'ACTIVE');

insert into bundle (name, cost, cookie_amount)
values ('cookies',4.99,100),
        ('cookie tray',9.99,205),
        ('cookie box',19.99,420);

insert into catalogue (id, id_catalogue, value) values
	(1,'award_tax', 5);

insert into log_type (id, name, template, status)
values (1,'Award','','ACTIVE');

insert into user_profile(status, user_id)
values ('INACTIVE', 1),
       ('INACTVIE', 2),
       ('INACTVIE', 3),
       ('INACTIVE', 4);
update jhi_user
set email = 'teamintuite@gmail.com'
where login = 'admin';

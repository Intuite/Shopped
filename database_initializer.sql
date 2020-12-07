# This must be ran only the first time the database is created
use Shopped;

# INITIALIZE TAG TYPES
insert into tag_type (name, description, status)
values ('Food Time', 'Moment of day', 'ACTIVE'),
       ('Origin','Place of origin', 'ACTIVE'),
       ('Festivity', 'Event of the year', 'ACTIVE'),
       ('Dish Type', 'Category in menu','ACTIVE'),
	   ('Taste', 'How te recipe taste','ACTIVE');

# INITIALIZE RECIPE TAGS
insert into recipe_tag (name, description, status, type_id)
values ('Breakfast','Food recomended for breakfast', 'ACTIVE', 1),
       ('Brunch','Food recomended for brunch', 'ACTIVE', 1),
       ('Lunch','Food recomended for lunch','ACTIVE', 1),
       ('Snack', 'Food recomended for a snack', 'ACTIVE', 1),
       ('Tea time', 'Food recomended durin tea time', 'ACTIVE', 1),
       ('Dinner', 'Food recomended for dinner', 'ACTIVE', 1),
       ('American', 'From the country of USA', 'INACTIVE', 2),
       ('Latin american', 'From a latin american country', 'ACTIVE', 2),
       ('Mexican', 'From the country of Mexico', 'ACTIVE', 2),
       ('French', 'Description', 'ACTIVE', 2),
       ('Japanese','From the country of Japan','ACTIVE', 2),
       ('Oriental','From a country in asia','ACTIVE', 2),
       ('Indu','From the country of India','ACTIVE', 2),
       ('African', 'From an african country','ACTIVE', 2),
       ('Chinese', 'From the country of China', 'ACTIVE', 2),
       ('Italian', 'From the country of Italy', 'ACTIVE', 2),
       ('Wedding', 'For a wedding event', 'ACTIVE', 3),
       ('Birthday', 'For a birthday event', 'ACTIVE', 3),
       ('Christmas', 'For a chrismas event', 'ACTIVE', 3),
       ('Holy week', 'For a holy week event', 'ACTIVE', 3),
       ('Easter', 'For a easter event', 'ACTIVE', 3),
       ('Baby shower', 'For a baby shower', 'ACTIVE', 3),
       ('Family reunion','For a family reunion event','ACTIVE', 3),
       ('Pasta', 'Menu type pasta', 'ACTIVE', 4),
       ('Sushi', 'Menu type sushi', 'ACTIVE', 4),
       ('Soup', 'Menu type soup', 'ACTIVE',  4),
       ('Salad', 'Menu type salad', 'ACTIVE', 4),
       ('Dessert', 'Menu type dessert', 'ACTIVE', 4),
	   ('Rice', 'Menu type rice','ACTIVE', 4),
	   ('Meat special', 'Menu type meats', 'ACTIVE', 4),
	   ('Sea food','Menu type sea food', 'ACTIVE', 4),
	   ('Sweet', 'Food that is categorized by the sweet taste it has', 'ACTIVE', 5),
       ('Salty', 'Food that is categorized by the salty taste it has', 'ACTIVE',5),
       ('Sour', 'Food that is categorized by the sour taste it has', 'ACTIVE',5),
       ('Spicy', 'Food that is categorized by the spicy taste it has', 'ACTIVE',5),
       ('Bitter', 'Food that is categorized by the bitter taste it has', 'ACTIVE',5);

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
       ('inches', 'in'),      # 11
	   ('units', 'u');        # 12

# INITIALIZE INGREDIENTS
insert into ingredient (name, unit_id, status)
values ('Lagsana noodles', 12, 'ACTIVE'),
       ('Chicken meat', 8, 'ACTIVE'),
       ('Vegetables', 8, 'ACTIVE'),
       ('Spinach', 8, 'ACTIVE'),
       ('Cheese', 8, 'ACTIVE'),
       ('Eggs', 12, 'ACTIVE'),
       ('Parsley', 8, 'ACTIVE'),
       ('Butter', 5, 'ACTIVE'),
       ('Onion', 8, 'ACTIVE'),
       ('Garlic', 8, 'ACTIVE'),
       ('Flour', 8, 'ACTIVE'),
       ('Milk', 7, 'ACTIVE'),
       ('Chicken broth', 8, 'ACTIVE'),
       ('Cream cheese', 8, 'ACTIVE'),
       ('Basil', 8, 'ACTIVE'),
       ('Oregano', 8, 'ACTIVE'),
       ('Brown sugar', 8, 'ACTIVE'),
       ('Vanilla extract', 7, 'ACTIVE'),
       ('Baking soda', 8, 'ACTIVE'),
       ('Water', 7, 'ACTIVE'),
       ('Chocolate chips', 8, 'ACTIVE'),
       ('Walnuts', 8, 'ACTIVE'),
       ('Garlic salt', 8, 'ACTIVE'),
       ('Paprika', 8, 'ACTIVE'),
       ('Pepper', 8, 'ACTIVE'),
       ('Poultry seasoning', 8, 'ACTIVE'),
       ('Oil', 7, 'ACTIVE'),
       ('Olive oil', 7, 'ACTIVE'),
       ('Carrot', 8, 'ACTIVE'),
       ('Celery', 8, 'ACTIVE'),
       ('Tomato paste', 5, 'ACTIVE'),
       ('Fennel seed', 8, 'ACTIVE'),
       ('Red pepper flakes', 8, 'ACTIVE'),
       ('Tomato', 8, 'ACTIVE'),
       ('Potato', 8, 'ACTIVE'),
       ('Cabbage', 8, 'ACTIVE'),
       ('Bay leaves', 8, 'ACTIVE'),
       ('Peas', 8, 'ACTIVE'),
       ('Spaghetti', 8, 'ACTIVE'),
       ('Shrimps', 8, 'ACTIVE'),
       ('Baby tomatoes', 8, 'ACTIVE'),
       ('Seafood broth', 8, 'ACTIVE'),
       ('Red wine vinegar', 8, 'ACTIVE'),
       ('Dijon mustard', 8, 'ACTIVE'),
       ('English cucumber', 8, 'ACTIVE'),
       ('Green bell pepper', 8, 'ACTIVE'),
       ('Kalamata olives', 8, 'ACTIVE'),
       ('Feta Cheese', 8, 'ACTIVE'),
       ('Salmon fillets', 8, 'ACTIVE'),
       ('Soy sauce', 8, 'ACTIVE'),
       ('Lemon juice', 7, 'ACTIVE'),
       ('Baking powder', 8, 'ACTIVE'),
       ('Bread crumbs', 8, 'ACTIVE'),
       ('Ground beef', 8, 'ACTIVE'),
       ('Ground pork', 8, 'ACTIVE'),
       ('Italian sausage', 8, 'ACTIVE'),
       ('Marinara sauce', 8, 'ACTIVE'),
       ('Glutinous white rice', 8, 'ACTIVE'),
       ('Rice vinegar', 8, 'ACTIVE'),
       ('Tuna can', 8, 'ACTIVE'),
       ('Mayonnaise', 8, 'ACTIVE'),
       ('Chili powder', 8, 'ACTIVE'),
       ('Wasabi paste', 8, 'ACTIVE'),
       ('Nori', 12, 'ACTIVE'),
       ('Cucumber', 8, 'ACTIVE'),
       ('Avocado', 12, 'ACTIVE');

insert into bundle (name, cost, cookie_amount)
values ('cookies',4.99,100),
        ('cookie tray',9.99,205),
        ('cookie box',19.99,420);

insert into catalogue (id, id_catalogue, value) values
	(1,'award_tax', 5);

insert into catalogue (id, id_catalogue, value) values
	(2,'cookie_withdraw_price', 40);

insert into log_type (id, name, template, status)
values (1,'Award','','ACTIVE'),
	   (2,'Post create','','ACTIVE'),
	   (3,'Post view','','ACTIVE'),
	   (4,'Post bite','','ACTIVE'),
	   (5,'Post comment','','ACTIVE');

insert into user_profile(status, user_id)
values ('ACTIVE', 1),
       ('ACTIVE', 2),
       ('ACTIVE', 3);

update jhi_user
set email = 'teamintuite@gmail.com'
where login = 'admin';

select @uauser := ua.user_id from jhi_user_authority ua inner join jhi_user u on ua.user_id = u.id where u.login = 'admin' and ua.authority_name like 'ROLE_USER';
delete from jhi_user_authority where user_id = @uauser and authority_name like 'ROLE_USER';


# INITIALIZE RECIPES
insert into recipe (name, portion, description, duration, creation, status, user_id)
values ('Chicken Lasagna', 15,
	 'To make the sauce, melt butter, onion and garlic over medium low heat. Cook until onion is softened, about 3 minutes. Add flour and cook for 1-2 minutes.

	Reduce heat to low. Combine milk and broth. Add a small amount at a time whisking to thicken. The mixture will become very thick, continue adding a little bit of liquid at a time whisking until smooth.

	Once all of the liquid has been added, stir in cream cheese until melted.
	Remove from heat and add 1 cup a cheese, dried basil and oregano.

	Assembly

	Combine cheese, eggs, parsley and spinach. Set aside.

	In a 9x13 pan, layer 4 noodles, sauce, cooked vegetables and half of the chicken. Sprinkle with 1/2 mozza, 1/4 cup parmesan and 1/3 of the sauce.

	Add another layer of noodles, chicken, cottage cheese mixture, sauce.  Top with noodles and sauce. Cover and bake 40 minutes.

	Uncover, top with cheese and bake 20-30 minutes more.'
	, 105, '2020-11-26 01:00:00'
	,'ACTIVE', 4),

	('Chocolate Chip Cookies', 24,
	 'Step 1
	Preheat oven to 350 degrees F (175 degrees C).

	Step 2
	Cream together the butter, white sugar, and brown sugar until smooth. Beat in the eggs one at a time, then stir in the vanilla. Dissolve baking soda in hot water. Add to batter along with salt. Stir in flour, chocolate chips, and nuts. Drop by large spoonfuls onto ungreased pans.

	Step 3
	Bake for about 10 minutes in the preheated oven, or until edges are nicely browned.
	'
	, 30, '2020-11-26 02:00:00','ACTIVE', 4),

	('Crispy Fried Chicken', 12,
	 'In a large shallow dish, combine 2-2/3 cups flour, 2 tablespoons garlic salt, 1 tablespoon paprika, 2-1/2 teaspoons pepper and 2-1/2 teaspoons poultry seasoning. In another shallow dish, beat eggs and 1-1/2 cups water; add 1 teaspoon salt and the remaining 1-1/3 cup flour and 1/2 teaspoon pepper. Dip chicken in egg mixture, then place in the flour mixture, a few pieces at a time. Turn to coat.
	In a deep-fat fryer, heat oil to 375°. Working in batches, fry chicken, several pieces at a time, until golden brown and a thermometer inserted into chicken reads 165°, about 7-8 minutes on each side. Drain on paper towels.
	'
	, 30, '2020-11-26 03:00:00','ACTIVE', 4),

	('Vegetable Soup', 6,
	 'Heat two tablespoons of the olive oil in a stockpot pot or Dutch oven over medium heat. Add the onions, carrots, celery, and the tomato paste. Cook, stirring often until the vegetables have softened and the onions are translucent; 8 to 10 minutes.

	Add the garlic, fennel, black pepper, 1/2 teaspoon of salt, and the red pepper flakes. Cook, while stirring, for one minute.

	Pour in the canned tomatoes and their juices as well as the stock/broth.

	Add the potatoes, cabbage, and the bay leaves. Raise the heat to medium-high and bring the soup to a boil. Partially cover the pot with a lid, and then reduce the heat to maintain a low simmer.

	Simmer for 20 minutes or until the potatoes, and other vegetables are tender. Add the frozen peas and cook for five more minutes.

	Remove the pot from the heat and remove the bay leaves. Stir in the cider vinegar (or lemon juice) and the remaining tablespoon of olive oil. Taste and season with more salt, pepper or vinegar. Serve.'
	, 40, '2020-11-26 04:00:00','ACTIVE', 4),

	('Garlic Shrimp Spaghetti', 4,
	 'Cook the pasta: Bring a large pot of salted water to the boil, add pasta and cook al dente. Rinse, drain, and set aside.
	Cook the broth: In a skillet with oil add garlic, then sun-dried tomatoes, smoked paprika, and broth. Cook until softened.
	Add the shrimps and spinach: Add the prawns and spinach to the skillet and cook for about 3 mins.
	Combine with pasta: Add the pasta and toss well, until nicely coated.
	'
	, 40, '2020-11-26 05:00:00','ACTIVE', 4),

	('Greek Salad', 4,
	 'Make the dressing: In a small bowl, whisk together the olive oil, vinegar, garlic, oregano, mustard, salt, and several grinds of pepper.

	On a large platter, arrange the cucumber, green pepper, baby tomatoes, feta cheese, onions, and olives. Drizzle with the dressing and very gently toss. Sprinkle with a few generous pinches of oregano and top with the mint leaves. Season to taste and serve.
	'
	, 15, '2020-11-26 06:00:00','ACTIVE', 4),

	('Grilled Salmon', 6,
	 'Step 1
	Season salmon fillets with pepper, garlic salt, and salt.

	Step 2
	In a small bowl, stir together soy sauce, brown sugar, water, and oil until sugar is dissolved. Place fish in a large resealable plastic bag with the soy sauce mixture, seal, and turn to coat. Refrigerate for at least 2 hours.

	Step 3
	Preheat grill for medium heat.

	Step 4
	Lightly oil grill grate. Place salmon on the preheated grill, and discard marinade. Cook salmon for 6 to 8 minutes per side, or until the fish flakes easily with a fork.
	'
	, 150, '2020-11-26 07:00:00','ACTIVE', 4),

	('Madeleine (Muffin)', 20,
	 '1. Preheat the oven to 180 degree
	2. Mix together eggs and sugar and vanilla and lemon juice for 8 minutes
	3. Add the flour and baking powder and baking soda, then mix well
	4. Add oil and mix
	5. fill the cupcakes to 3/4 of their sizes
	6. bake for 15min and enjoy
	'
	, 30, '2020-11-26 08:00:00','ACTIVE', 4),

	('Meatballs', 8,
	 'In a small bowl, stir bread crumbs with milk until evenly combined. Let sit 15 minutes, or while you prep other ingredients.

	In a large bowl, use your hands to combine beef, pork, sausage, onion, and garlic. Season with salt and pepper, then gently stir in bread crumb mixture, eggs and parsley until just combined. Form mixture into 1" balls.
	In a large high-sided skillet over medium heat, heat oil. Working in batches, sear meatballs on all sides to develop a crust. Set meatballs aside, reduce heat to medium-low, and add sauce to skillet. Bring sauce to a simmer then immediately add meatballs back to skillet. Cover and simmer until cooked through, about 8 minutes more.'
	, 60, '2020-11-26 09:00:00','ACTIVE', 4),

	('Spicy Tuna Sushi Roll', 4,
	 'Step 1
	Bring the rice, water, and vinegar to a boil in a saucepan over high heat. Reduce heat to medium-low, cover, and simmer until the rice is tender, and the liquid has been absorbed, 20 to 25 minutes. Let stand, covered, for about 10 minutes to absorb any excess water. Set rice aside to cool.

	Step 2
	Lightly mix together the tuna, mayonnaise, chili powder, and wasabi paste in a bowl, breaking the tuna apart but not mashing it into a paste.

	Step 3
	To roll the sushi, cover a bamboo sushi rolling mat with plastic wrap. Lay a sheet of nori, rough side up, on the plastic wrap. With wet fingers, firmly pat a thick, even layer of prepared rice over the nori, covering it completely. Place about 1 tablespoon each of diced cucumber, carrot, and avocado in a line along the bottom edge of the sheet, and spread a line of tuna mixture alongside the vegetables.

	Step 4
	Pick up the edge of the bamboo rolling sheet, fold the bottom edge of the sheet up, enclosing the filling, and tightly roll the sushi into a thick cylinder. Once the sushi is rolled, wrap it in the mat and gently squeeze to compact it tightly. Cut each roll into 6 pieces, and refrigerate until serve
	'
	, 75, '2020-11-26 10:00:00','ACTIVE', 4);

# INITIALIZE RECIPESHASRECIPETAG
insert into recipe_has_recipe_tag  (status, recipe_id, recipe_tag_id)
values  ('ACTIVE', 1, 6),
		('ACTIVE', 1, 17),
		('ACTIVE', 1, 25),
		('ACTIVE', 2, 4),
		('ACTIVE', 2, 7),
		('ACTIVE', 2, 29),
		('ACTIVE', 3, 3),
		('ACTIVE', 3, 7),
		('ACTIVE', 3, 31),
		('ACTIVE', 4, 6),
		('ACTIVE', 4, 10),
		('ACTIVE', 4, 27),
		('ACTIVE', 5, 17),
		('ACTIVE', 5, 25),
		('ACTIVE', 5, 32),
		('ACTIVE', 6, 13),
		('ACTIVE', 6, 28),
		('ACTIVE', 7, 3),
		('ACTIVE', 7, 21),
		('ACTIVE', 7, 32),
		('ACTIVE', 8, 5),
		('ACTIVE', 8, 19),
		('ACTIVE', 8, 29),
		('ACTIVE', 9, 3),
		('ACTIVE', 9, 24),
		('ACTIVE', 9, 31),
		('ACTIVE', 10, 6),
		('ACTIVE', 10, 11),
		('ACTIVE', 10, 26);

# INITIALIZE RECIPESHASINGREDIENT
insert into recipe_has_ingredient (amount, status, ingredient_id, recipe_id)
values  (12, 'ACTIVE', 1, 1),
		(4, 'ACTIVE', 2, 1),
		(3, 'ACTIVE', 3, 1),
		(10, 'ACTIVE', 4, 1),
		(400, 'ACTIVE', 5, 1),
		(2, 'ACTIVE', 6, 1),
		(2, 'ACTIVE', 7, 1),
		(100, 'ACTIVE', 8, 2),
		(2, 'ACTIVE', 6, 2),
		(100, 'ACTIVE', 17, 2),
		(2, 'ACTIVE', 18, 2),
		(20, 'ACTIVE', 19, 2),
		(300, 'ACTIVE', 11, 2),
		(150, 'ACTIVE', 21, 2),
		(200, 'ACTIVE', 11, 3),
		(20, 'ACTIVE', 10, 3),
		(5, 'ACTIVE', 24, 3),
		(3, 'ACTIVE', 25, 3),
		(2, 'ACTIVE', 26, 3),
		(2, 'ACTIVE', 6, 3),
		(500, 'ACTIVE', 13, 3),
		(200, 'ACTIVE', 9, 4),
		(50, 'ACTIVE', 31, 4),
		(20, 'ACTIVE', 10, 4),
		(500, 'ACTIVE', 34, 4),
		(600, 'ACTIVE', 13, 4),
		(200, 'ACTIVE', 35, 4),
		(435, 'ACTIVE', 39, 5),
		(800, 'ACTIVE', 40, 5),
		(40, 'ACTIVE', 28, 5),
		(500, 'ACTIVE', 34, 5),
		(100, 'ACTIVE', 41, 5),
		(60, 'ACTIVE', 13, 5),
		(30, 'ACTIVE', 28, 6),
		(40, 'ACTIVE', 43, 6),
		(30, 'ACTIVE', 44, 6),
		(250, 'ACTIVE', 45, 6),
		(200, 'ACTIVE', 34, 6),
		(80, 'ACTIVE', 48, 6),
		(50, 'ACTIVE', 47, 6),
		(1000, 'ACTIVE', 49, 7),
		(50, 'ACTIVE', 50, 7),
		(30, 'ACTIVE', 17, 7),
		(50, 'ACTIVE', 27, 7),
		(220, 'ACTIVE', 11, 8),
		(6, 'ACTIVE', 6, 8),
		(180, 'ACTIVE', 27, 8),
		(25, 'ACTIVE', 51, 8),
		(150, 'ACTIVE', 53, 9),
		(150, 'ACTIVE', 12, 9),
		(500, 'ACTIVE', 54, 9),
		(500, 'ACTIVE', 55, 9),
		(30, 'ACTIVE', 10, 9),
		(300, 'ACTIVE', 58, 10),
		(20, 'ACTIVE', 59, 10),
		(150, 'ACTIVE', 60, 10),
		(100, 'ACTIVE', 29, 10),
		(1, 'ACTIVE', 66, 10);

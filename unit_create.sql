use shopped;
delete from ingredient where id > 0;
delete from unit where id > 0;
insert into unit (name, abbrev) 
values 
	("drop","dr"),
    ("dash","ds"),
    ("pinch","pn"),
    ("teaspoon","tsp"),
    ("cup","C"),
    ("liters","l"),
    ("milliliter","ml"),
    ("grams","g"),
    ("kilograms","kg"),
    ("centimeters","cm"),
    ("inches","in");
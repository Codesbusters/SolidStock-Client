package fr.codesbusters.solidstock.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SolidStockModel {

    public static final ObservableList<ProductModel> products;
    public static final ObservableList<String> languages;
    public static final ObservableList<UsersModel> users;

    static {
        products = FXCollections.observableArrayList(
                ProductModel.ofSplit(1, "Jambon", "Un joli jambon tout droit venu de corse", "Charcuterie", "123456789", 10, 5.00, 10, 0),
                ProductModel.ofSplit(2, "Pain", "Un joli pain tout droit venu de la boulangerie des Taveaux", "Boulangerie", "123456789", 10, 5, 10, 0),
                ProductModel.ofSplit(3, "Pain au chocolat", "Un joli pain au chocolat tout droit venu de la boulangerie des Taveaux", "Boulangerie", "123456789", 10, 5, 10, 0),
                ProductModel.ofSplit(4, "Croissant", "Un joli croissant tout droit venu de la boulangerie des Taveaux", "Boulangerie", "123456789", 10, 5, 10, 0),
                ProductModel.ofSplit(5, "Pain aux raisins", "Un joli pain aux raisins tout droit venu de la boulangerie des Taveaux", "Boulangerie", "123456789", 10, 5, 10, 0),
                ProductModel.ofSplit(6, "Rosette", "Une jolie rosette tout droit venue de Lyon", "Charcuterie", "123456789", 10, 5, 10, 0),
                ProductModel.ofSplit(7, "Saucisson", "Un joli saucisson tout droit venu de Lyon", "Charcuterie", "123456789", 10, 5, 10, 0),
                ProductModel.ofSplit(8, "Saucisson sec", "Un joli saucisson sec tout droit venu de Lyon", "Charcuterie", "123456789", 10, 5, 10, 0),
                ProductModel.ofSplit(9, "Saucisson à l'ail", "Un joli saucisson à l'ail tout droit venu de Lyon", "Charcuterie", "123456789", 10, 5, 10, 0),
                ProductModel.ofSplit(10, "Saucisson à la pistache", "Un joli saucisson à la pistache tout droit venu de Lyon", "Charcuterie", "123456789", 10, 5, 10, 0),
                ProductModel.ofSplit(11, "Saucisson à la noisette", "Un joli saucisson à la noisette tout droit venu de Lyon", "Charcuterie", "123456789", 10, 5, 10, 0),
                ProductModel.ofSplit(12, "Comté", "Un joli comté tout droit venu de Franche comté", "Fromage", "123456789", 10, 5.23, 10, 0),
                ProductModel.ofSplit(13, "Morbier", "Un joli morbier tout droit venu de Franche comté", "Fromage", "123456789", 10, 5, 10, 0),
                ProductModel.ofSplit(14, "Mont d'or", "Un joli mont d'or tout droit venu de Franche comté", "Fromage", "123456789", 10, 5.63, 10, 0),
                ProductModel.ofSplit(15, "Cancoillotte", "Une joli cancoillotte tout droit venu de Franche comté", "Fromage", "123456789", 10, 5, 10, 0),
                ProductModel.ofSplit(16, "Munster", "Un joli munster tout droit venu de Franche comté", "Fromage", "123456789", 10, 5, 10, 0),
                ProductModel.ofSplit(17, "Bleu de Gex", "Un joli bleu de Gex tout droit venu de Franche comté", "Fromage", "123456789", 10, 5, 10, 0),
                ProductModel.ofSplit(18, "Bleu de Bresse", "Un joli bleu de Bresse tout droit venu de Franche comté", "Fromage", "123456789", 10, 5, 10, 0),
                ProductModel.ofSplit(19, "Camambert", "Un joli camambert tout droit venu de Normandie", "Fromage", "123456789", 10, 5, 10, 0)
        );

        languages = FXCollections.observableArrayList(
                "Français",
                "English"
        );

        users = FXCollections.observableArrayList(
                UsersModel.ofSplit(1, "Jean","Didier", 3, "bonjouurtoi", 3, "Ouvrier", "Fatiguant","didier.jean@solidstock.fr","01 02 03 04 05", "DidiJO"),
                UsersModel.ofSplit(2, "Jean", "Robert",2, "salut", 6, "comptable", "Chiant à mourir","robert.jean@solidstock.fr","01 02 03 04 05", "RobJO"),
                UsersModel.ofSplit(3, "Jean","Pascal" ,1, "hello", 8, "chef", "Je commande","pascal.jean@solidstock.fr","01 02 03 04 05", "PascJO"),
                UsersModel.ofSplit(4, "Jean","Marie" ,2, "buenos dias", 9, "patron", "Je fais ce que je veux","marie.jean@solidstock.fr","01 02 03 04 05", "MarreJO"),
                UsersModel.ofSplit(5, "Jean","Pierre" ,5, "hey", 2, "actionnaire", "J'aime l'argent","pierre.jean@solidstock.fr","01 02 03 04 05", "CaillouxJO"),
                UsersModel.ofSplit(6, "Jean","François" ,6, "yo", 5, "Livreur", "Je vais vite","françois.jean@solidstock.fr","01 02 03 04 05", "FRJO")
        );
    }
}

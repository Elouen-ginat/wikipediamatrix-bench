package fr.univrennes1.istic.wikipediamatrix.Web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomePage {

    @GetMapping("/")
    public String Welcom() {
        String hello = "<b>This is a Rest API that all you to get wikipedia tables in an array like form convertible to CSV. </b><br><br>";

        String explanation = "- Got to \"<b>/table_index?name=(wikipedia page name)&index=(index of the table amoung wikitables)</b>\" <br>"
                            +   "      to get the specific table you want from the wikipedia page. A list of list is returned being the rows and the columns.<br><br>"
                            +   "- Got to \"<b>/table_all?name=(wikipedia page name)</b>\" <br>"
                            +   "      to get all the tables from the wikipedia page. A 3D List is returned being the tables, the rows and the columns.<br><br>"
                            +   "- You can add /reformat at the right of \"/table_index\" or \"/table_all\" to get an HTML view of the transformed table.";

        return hello + explanation;
    }
}

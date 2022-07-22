package pages;

import com.codeborne.selenide.ElementsCollection;

import static com.codeborne.selenide.Selenide.*;

public class WebTablesPage {

    public WebTablesPage addNewRecord() {
        $("#addNewRecordButton").click();
        return page(WebTablesPage.class);
    }

    public class WebTables {
        public ElementsCollection results() {
            return $$("#ires li.g");
        }
    }
}

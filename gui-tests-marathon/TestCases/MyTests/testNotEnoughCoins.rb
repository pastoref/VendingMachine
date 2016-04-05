#{{{ Marathon
require_fixture 'default'
#}}} Marathon

def test

    $java_recorded_version="1.8.0_65"
    with_window("Vending Machine") {
        click("img/0.gif")
        click("img/1.gif")
        click("img/ok.gif")
        assert_p("display", "Text", "More coins")
    }

end

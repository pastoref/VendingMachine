#{{{ Marathon
require_fixture 'default'
#}}} Marathon

def test

    $java_recorded_version="1.8.0_65"
    with_window("HardwareCoinBoxSimulator") {
        click("0.20")
        select("display", "20")
    }

    with_window("Vending Machine") {
        click("img/0.gif")
        click("img/1.gif")
        click("img/ok.gif")
        doubleclick("img/0.gif")
        click("img/ok.gif")
        assert_p("display", "Text", "Not valid")
        click("img/ce.gif")
        click("display")
        rightclick("display")
        assert_p("display", "Text", "")
    }

end

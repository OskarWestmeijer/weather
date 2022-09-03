package westmeijer.oskar.weatherapi.repository.openweatherapi;

public enum RequestParamPlaceholders {

    APP_ID_CHAR_SEQUENCE("{app_id}"),
    LOCATION_CODE_CHAR_SEQUENCE("{location_code}");

    private final String value;


    RequestParamPlaceholders(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

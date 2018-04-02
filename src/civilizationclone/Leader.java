package civilizationclone;

public enum Leader {
    ZEDONG(new String[]{"Beijing", "Nanjing", "Shanghai", "Guangzhou", "Chongqing", "Wuhan", "Chengdu"}),
    HITLER(new String[]{"Berlin", "Munich", "Hamberg", "Cologne", "Essen", "Stuttgart", "Dortmund"}),
    MUSSOLINI(new String[]{"Rome", "Milan", "Florence", "Turin", "Genoa", "Pisa", "Siena"}),
    STALIN(new String[]{"Moscow", "St. Petersburg", "Stalingrad", "Kiev", "Tashkent", "Baku", "Kharkov"}),
    CHURCHILL(new String[]{"London", "Manchester", "Yorkshire", "Birmingham", "Glasgow", "Liverpool", "Nottingham"}),
    ROOSEVELT(new String[]{"Washington", "New York", "Boston", "Chicago", "Los Angeles", "San Francisco", "Seattle"});

    private String[] cityNames;

    private Leader(String[] cityNames) {
        this.cityNames = cityNames;
    }

    public String[] getCityNames() {
        return cityNames;
    }
}

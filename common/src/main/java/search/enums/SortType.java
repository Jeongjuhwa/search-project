package search.enums;

public enum SortType {
    ACCURACY("정확도순","accuracy","sim"),
    RECENCY("최신순", "recency", "date")
            ;

    private final String desc;
    private final String kakaoSortType;
    private final String naverSortType;
    SortType(String desc, String kakaoSortType, String naverSortType) {
        this.desc = desc;
        this.kakaoSortType = kakaoSortType;
        this.naverSortType = naverSortType;
    }
    public String convertToKakaoSortType(){
        return this.kakaoSortType;
    }
    public String convertTonaverSortType(){
        return this.naverSortType;
    }
}

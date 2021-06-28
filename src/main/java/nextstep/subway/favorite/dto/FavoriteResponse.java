package nextstep.subway.favorite.dto;

import nextstep.subway.favorite.domain.Favorite;
import nextstep.subway.station.dto.StationResponse;

public class FavoriteResponse {
    private Long id;
    private StationResponse source;
    private StationResponse target;

    public FavoriteResponse() {
    }

    public FavoriteResponse(Favorite favorite) {
        this.id = favorite.getId();
        this.source = favorite.getSource().toResponse();
        this.target = favorite.getTarget().toResponse();
    }

    public static FavoriteResponse of(Favorite favorite) {
        return new FavoriteResponse(favorite);
    }

    public Long getId() {
        return id;
    }

    public StationResponse getSource() {
        return source;
    }

    public StationResponse getTarget() {
        return target;
    }
}
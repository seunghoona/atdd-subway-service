package nextstep.subway.line.ui;


import nextstep.subway.line.application.PathService;
import nextstep.subway.line.dto.PathRequest;
import nextstep.subway.line.dto.PathResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/paths")
public class PathController {

    PathService pathService;

    public PathController(PathService pathService) {
        this.pathService = pathService;
    }

    @GetMapping
    public ResponseEntity<PathResponse> findStationPath(@RequestParam PathRequest pathRequest) {
        PathResponse pathResponse = pathService.findStationPath(pathRequest);
        return ResponseEntity.ok().body(pathResponse);
    }

}
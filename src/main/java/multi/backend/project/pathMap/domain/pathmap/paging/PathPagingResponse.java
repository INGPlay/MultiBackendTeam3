package multi.backend.project.pathMap.domain.pathmap.paging;

import lombok.Getter;
import multi.backend.project.pathMap.domain.pathmap.PathInfoResponse;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
public class PathPagingResponse {
    private final List<PathInfoResponse> pathInfoResponses;

    private final int totalCount;
    private final int totalPageNum;

    private final int currentPageNum;
    private final int pageSize;

    private final int startNum;
    private final int endNum;

    private final List<Integer> pageNumList;

    private final boolean hasPrevious;
    private final boolean hasNext;

    private final Integer previousNum;
    private final Integer nextNum;

    public PathPagingResponse(List<PathInfoResponse> pathInfoResponses, int currentPageNum, int pageSize) {
        this.totalCount = pathInfoResponses.size();

        this.currentPageNum = currentPageNum;
        this.pageSize = pageSize;

        this.totalPageNum = (int) (Math.ceil((totalCount) / (double)pageSize));

        this.endNum = initEndNum();
        this.startNum = initStartNum();

        this.pageNumList = IntStream.rangeClosed(getStartNum(), getEndNum()).boxed().collect(Collectors.toList());

        this.hasPrevious = initPrevious();
        this.hasNext = initNext();

        this.previousNum = initPreviousNum();
        this.nextNum = initNextNum();

        this.pathInfoResponses = initPathInfoResponsePage(pathInfoResponses);
    }

    private List<PathInfoResponse> initPathInfoResponsePage(List<PathInfoResponse> pathInfoResponses) {
        int end = currentPageNum * pageSize;
        int start = end - pageSize;

        if (end > pathInfoResponses.size()){
            end = pathInfoResponses.size();
        }

        return pathInfoResponses.subList(start, end);
    }

    private int initEndNum(){
        int endNum = (int) (Math.ceil(currentPageNum / (double) pageSize) * pageSize);

        if (endNum > totalPageNum){
            return totalPageNum;
        }

        return endNum;
    }

    private int initStartNum(){
        return (int) (Math.ceil(currentPageNum / (double) pageSize) * pageSize) - pageSize + 1;
    }

    private boolean initPrevious(){
        if (startNum <= 1){
            return false;
        }
        return true;
    }

    private boolean initNext(){
        if (endNum >= totalPageNum) {
            return false;
        }
        return true;
    }

    private Integer initPreviousNum(){
        int result = startNum - pageSize;

        // 이전 페이지가 없다면
        if (!hasPrevious){
            return null;
        }

        return result;
    }

    private Integer initNextNum(){
        int result = startNum + pageSize;

        // 다음 페이지가 없다면
        if (!hasNext){
            return null;
        }

        return result;
    }
}

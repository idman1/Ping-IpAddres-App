package idman.server.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.hibernate.type.descriptor.java.IntegerJavaType.ZERO;

public class AppUtils {
    public static final int ONE = 1;
    public static final int DEFAULT_PAGE_NUMBER = 1;
    public static  final int DEFAULT_PAGE_LIMIT = 10;

    public static Pageable buildPageRequest(int page, int items){
        if (page<=ZERO) page=DEFAULT_PAGE_NUMBER;
        if (items<=ZERO) items = DEFAULT_PAGE_LIMIT;
        page-=ONE;
        return PageRequest.of(page, items);
    }
}

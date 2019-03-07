package io.pivotal.pal.tracker.backlog;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.client.RestOperations;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProjectClient {

    private final RestOperations restOperations;
    private final String endpoint;
    private Map<Long, ProjectInfo> projectInfoMap = new ConcurrentHashMap<Long, ProjectInfo>();

    public ProjectClient(RestOperations restOperations, String registrationServerEndpoint) {
        this.restOperations = restOperations;
        this.endpoint = registrationServerEndpoint;
    }

    @HystrixCommand(fallbackMethod = "getProjectFromCache")
    public ProjectInfo getProject(long projectId) {
        projectInfoMap.put(projectId, restOperations.getForObject(endpoint + "/projects/" + projectId, ProjectInfo.class));
        return getProjectFromCache(projectId);
    }

    private ProjectInfo getProjectFromCache(long projectId){
        return (ProjectInfo) projectInfoMap.get(projectId);
    }
}

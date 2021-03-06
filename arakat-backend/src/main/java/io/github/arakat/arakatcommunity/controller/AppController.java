package io.github.arakat.arakatcommunity.controller;

import io.github.arakat.arakatcommunity.model.App;
import io.github.arakat.arakatcommunity.model.BaseResponse;
import io.github.arakat.arakatcommunity.model.TablePathResponse;
import io.github.arakat.arakatcommunity.repository.AppRepository;
import io.github.arakat.arakatcommunity.repository.TaskRepository;
import io.github.arakat.arakatcommunity.service.AppService;
import io.github.arakat.arakatcommunity.utils.ApiResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AppController {

    private final AppRepository appRepository;
    private final AppService appService;

    @Autowired
    public AppController(AppRepository appRepository, AppService appService) {
        this.appRepository = appRepository;
        this.appService = appService;
    }

    @RequestMapping(value = "/get-all-apps", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<BaseResponse> getAllApps() {
        List<App> apps = appRepository.findAll();

        return ApiResponseUtils.createResponseEntity(200,
                String.format(ApiResponseUtils.getUserMessageSuccess(), "Get all apps"),
                String.format(ApiResponseUtils.getDevMessageSuccess(), "Get all apps", "App"),
                apps, HttpStatus.OK);
    }

    @RequestMapping(value = "/get-all-apps-with-written-tables", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<BaseResponse> getAllAppsWithWrittenTables() {
        List<TablePathResponse> tablePathResponseList = appService.getAllAppsWithWrittenTables();

        return ApiResponseUtils.createResponseEntity(200,
                String.format(ApiResponseUtils.getUserMessageSuccess(), "Get app ids with written tables"),
                String.format(ApiResponseUtils.getDevMessageSuccess(), "Get app ids with written tables", "TablePathResponse"),
                tablePathResponseList, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete-app{appId}", produces = { "application/json" }, method = RequestMethod.DELETE)
    public ResponseEntity<BaseResponse> deleteApp(@PathVariable String appId) {
        appRepository.deleteAppByAppId(appId);

        return ApiResponseUtils.createResponseEntity(200,
                String.format(ApiResponseUtils.getUserMessageSuccess(), "Delete app by appId"),
                String.format(ApiResponseUtils.getDevMessageSuccess(), "Delete app by appId", "App"),
                null, HttpStatus.OK);
    }
}

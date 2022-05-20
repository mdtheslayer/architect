package com.microservices.license.controller;

import com.microservices.license.model.License;
import com.microservices.license.service.LicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "license-controller/organization/{organizationId}/license")
public class LicenseController {
    @Autowired
    private LicenseService licenseService;

    @RequestMapping(value = "/{licenseId}", method = RequestMethod.GET)
    public ResponseEntity <License> getLicense(
            @PathVariable("organizationId") String organizationId,
            @PathVariable("licenseId") String licenseId){
        License license = licenseService
                .getLicense(licenseId, organizationId);
        license.add(
                linkTo(methodOn(LicenseController.class)
                        .getLicense(organizationId, license.getLicenseId()))
                        .withSelfRel(),
                linkTo(methodOn(LicenseController.class)
                        .createLicense(organizationId, license, null))
                        .withRel("createLicense"),
                linkTo(methodOn(LicenseController.class)
                        .updateLicense(organizationId, license))
                        .withRel("updateLicense"),
                linkTo(methodOn(LicenseController.class)
                        .deleteLicense(organizationId, license.getLicenseId()))
                        .withRel("deleteLicense, heh"));
        return ResponseEntity.ok(license);
    }

    @RequestMapping(value = "/{licenseId}", method = RequestMethod.PUT)
    public ResponseEntity <String> updateLicense(
            @PathVariable("organizationId") String organizationId,
            @RequestBody License request){
        return ResponseEntity.ok(licenseService.updateLicense(request,organizationId));
    }

    @PostMapping
    public ResponseEntity<String> createLicense(@PathVariable("organizationId") String organizationId, @RequestBody License request,
                                                @RequestHeader(value = "Accept-Language",required = false) Locale locale) {
        return ResponseEntity.ok(licenseService.createLicense(request, organizationId, locale));
    }

    @RequestMapping(value = "/{licenseId}", method = RequestMethod.DELETE)
    public ResponseEntity <String> deleteLicense(
            @PathVariable("organizationId") String organizationId,
            @PathVariable("licenseId") String licenseId){
        return ResponseEntity.ok(licenseService.deleteLicense(licenseId,organizationId));
    }
}

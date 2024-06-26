package ru.custom.atlassian.hibernate.validator.config.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import ru.custom.atlassian.hibernate.validator.config.dto.DemoDto;
import ru.custom.atlassian.hibernate.validator.config.service.DemoValidationService;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@RequiredArgsConstructor
@Validated
@Path("/validation")
@Produces({MediaType.APPLICATION_JSON})
public class DemoValidationResource {

  private final DemoValidationService demoValidationService;

  @GET
  @Path("/using_query_param")
  public String validateQueryParameter(
      @Valid
          @Max(value = 10)
          @QueryParam("numberParam")
          final Integer numberParam) {
    return "Success validation query param " + numberParam;
  }

  @GET
  @Path("/using_path_param/{numberParam}")
  public String validatePathParameter(
      @Valid
          @Min(value = 20)
          @PathParam("numberParam")
          final Integer numberParam) {
    return "Success validation path param " + numberParam;
  }

  @POST
  @Path("/using_json_and_path_param/{someParam}")
  @Consumes({MediaType.APPLICATION_JSON})
  public String validateJson(
      @Valid
          @Min(value = 30)
          @PathParam("someParam")
          final Integer numberParam,
      @Valid @NotNull final DemoDto json) {
    return String.format("Success validation path param %s and request body %s", numberParam, json);
  }

  @GET
  @Path("/call_service_with_validated_param")
  public String callServiceWithValidatedParam(
      @Valid @NotNull @QueryParam("someNumberParam")
          final Integer someNumberParam) {
    demoValidationService.testMethodParamValidation(someNumberParam);
    return "Success called service with validated param";
  }
}

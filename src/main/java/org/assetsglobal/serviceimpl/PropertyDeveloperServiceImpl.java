package org.assetsglobal.serviceimpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.assetsglobal.dto.PropertyDeveloperRequest;
import org.assetsglobal.dto.PropertyDeveloperResponse;
import org.assetsglobal.entity.PropertyDeveloper;
import org.assetsglobal.enums.PropertyType;
import org.assetsglobal.exception.IllegalArgumentException;
import org.assetsglobal.repository.PropertyDeveloperRepository;
import org.assetsglobal.service.PropertyDeveloperService;
import org.assetsglobal.utility.ResponseStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PropertyDeveloperServiceImpl implements PropertyDeveloperService {

	@Autowired
	private PropertyDeveloperRepository sellerDashboardRepository;

	@Autowired
	private ResponseStructure<PropertyDeveloperResponse> responseStructure;

	// @Autowired
	// private SellerRepository sellerRepository;

	// @Override
	// public ResponseEntity<ResponseStructure<SellerDashboardResponse>>
	// addSellerWithProperty(
	// SellerDashboardRequest sellerDashboardRequest) {
	//
	// verify(sellerDashboardRequest);
	//
	// SellerDashboard sellerDashboard =
	// sellerDashboardRepository.save(mapToEntity(sellerDashboardRequest));
	// SellerDashboardResponse sellerDashboardResponse =
	// mapToResponse(sellerDashboard);
	//
	// return ResponseEntity.ok(responseStructure.setData(sellerDashboardResponse)
	// .setMessage("Hey " + sellerDashboardRequest.getSellerName() + "you are added
	// as a Seller")
	// .setStatusCode(HttpStatus.OK.value()));
	// }

	@Override
	public ResponseEntity<ResponseStructure<List<PropertyDeveloperResponse>>> fetchAllSellerWithProperty() {

		List<PropertyDeveloper> properties = sellerDashboardRepository.findAll();

		List<PropertyDeveloperResponse> collect = properties.stream().map(property -> mapToResponse(property))
				.collect(Collectors.toList());

		ResponseStructure<List<PropertyDeveloperResponse>> responseStructure = new ResponseStructure<>();
		responseStructure.setData(collect);
		responseStructure.setMessage("The numbers of property fetched is " + collect.size());
		responseStructure.setStatusCode(HttpStatus.OK.value());

		return ResponseEntity.ok(responseStructure);
	}

	@Override
	public ResponseEntity<ResponseStructure<PropertyDeveloperResponse>> fetchPropertyById(int sellerDaashboardId) {
		return sellerDashboardRepository.findById(sellerDaashboardId).map(property -> {
			responseStructure = responseStructure.setData(mapToResponse(property)).setStatusCode(sellerDaashboardId)
					.setMessage(null);
			return ResponseEntity.ok().body(responseStructure);
		}).orElseThrow(() -> new RuntimeException("Property not found"));
	}

	// Mappers

	private PropertyDeveloperResponse mapToResponse(PropertyDeveloper sellerDashboard) {

		PropertyDeveloperResponse sellerDashboardResponse = new PropertyDeveloperResponse();
		sellerDashboardResponse.setSellerDashboarId(sellerDashboard.getSellerDashboarId());
		sellerDashboardResponse.setSellerName(sellerDashboard.getSellerName());
		sellerDashboardResponse.setSellerEmail(sellerDashboard.getSellerEmail());
		sellerDashboardResponse.setSellerMobileNumber(sellerDashboard.getSellerMobileNumber());
		sellerDashboardResponse.setSellerLocation(sellerDashboard.getSellerLocation());
		sellerDashboardResponse.setPropertyType(sellerDashboard.getPropertyType());
		sellerDashboardResponse.setTransactionType(sellerDashboard.getTransactionType());
		sellerDashboardResponse.setConstructionStatus(sellerDashboard.getConstructionStatus());
		sellerDashboardResponse.setBhk(sellerDashboard.getBhk());
		sellerDashboardResponse.setBathroom(sellerDashboard.getBathroom());
		sellerDashboardResponse.setBalcony(sellerDashboard.getBalcony());
		sellerDashboardResponse.setFurnishedType(sellerDashboard.getFurnishedType());
		sellerDashboardResponse.setParking(sellerDashboard.getParking());
		sellerDashboardResponse.setOpenParking(sellerDashboard.getOpenParking());
		sellerDashboardResponse.setPossessionDate(sellerDashboard.getPossessionDate());
		sellerDashboardResponse.setCost(sellerDashboard.getCost());
		sellerDashboardResponse.setMaintainceCharge(sellerDashboard.getMaintainceCharge());
		sellerDashboardResponse.setBrockrageCharge(sellerDashboard.getBrockrageCharge());
		sellerDashboardResponse.setBuiltUpArea(sellerDashboard.getBuiltUpArea());
		sellerDashboardResponse.setCarpetArea(sellerDashboard.getCarpetArea());
		sellerDashboardResponse.setArea(sellerDashboard.getArea());
		sellerDashboardResponse.setFacing(sellerDashboard.getFacing());
		sellerDashboardResponse.setPropertyAddress(sellerDashboard.getPropertyAddress());
		sellerDashboardResponse.setReraId(sellerDashboard.getReraId());
		sellerDashboardResponse.setPropertyDescription(sellerDashboard.getPropertyDescription());
		sellerDashboardResponse.setCity(sellerDashboard.getCity());
		sellerDashboardResponse.setPropertyLocation(sellerDashboard.getPropertyLocation());
		sellerDashboardResponse.setState(sellerDashboard.getState());
		sellerDashboardResponse.setLandType(sellerDashboard.getLandType());

		return sellerDashboardResponse;
	}

	private PropertyDeveloper mapToEntity(PropertyDeveloperRequest sellerDashboardRequest) {
		PropertyDeveloper sellerDashboard = new PropertyDeveloper();
		// Seller details
		sellerDashboard.setSellerName(sellerDashboardRequest.getSellerName());
		sellerDashboard.setSellerEmail(sellerDashboardRequest.getSellerEmail());
		sellerDashboard.setSellerMobileNumber(sellerDashboardRequest.getSellerMobileNumber());
		sellerDashboard.setSellerLocation(sellerDashboardRequest.getSellerLocation());

		// Property details that seller is going to publish
		sellerDashboard.setPropertyType(sellerDashboardRequest.getPropertyType());
		sellerDashboard.setTransactionType(sellerDashboardRequest.getTransactionType());
		sellerDashboard.setConstructionStatus(sellerDashboardRequest.getConstructionStatus());
		sellerDashboard.setBhk(sellerDashboardRequest.getBhk());
		sellerDashboard.setBalcony(sellerDashboardRequest.getBalcony());
		sellerDashboard.setBathroom(sellerDashboardRequest.getBathroom());
		sellerDashboard.setFurnishedType(sellerDashboardRequest.getFurnishedType());
		sellerDashboard.setParking(sellerDashboardRequest.getParking());
		sellerDashboard.setOpenParking(sellerDashboardRequest.getOpenParking());
		sellerDashboard.setPossessionDate(sellerDashboardRequest.getPossessionDate());
		sellerDashboard.setCost(sellerDashboardRequest.getCost());
		sellerDashboard.setMaintainceCharge(sellerDashboardRequest.getMaintainceCharge());
		sellerDashboard.setBrockrageCharge(sellerDashboardRequest.getBrockrageCharge());
		sellerDashboard.setBuiltUpArea(sellerDashboardRequest.getBuiltUpArea());
		sellerDashboard.setCarpetArea(sellerDashboardRequest.getCarpetArea());
		sellerDashboard.setArea(sellerDashboardRequest.getArea());
		sellerDashboard.setFacing(sellerDashboardRequest.getFacing());
		sellerDashboard.setPropertyAddress(sellerDashboardRequest.getPropertyAddress());
		sellerDashboard.setReraId(sellerDashboardRequest.getReraId());
		sellerDashboard.setPropertyDescription(sellerDashboardRequest.getPropertyDescription());
		sellerDashboard.setCity(sellerDashboardRequest.getCity());
		sellerDashboard.setPropertyLocation(sellerDashboardRequest.getPropertyLocation());
		sellerDashboard.setState(sellerDashboardRequest.getState());
		sellerDashboard.setLandType(sellerDashboardRequest.getLandType());
//sellerDashboard.setla
		return sellerDashboard;
	}

	private void verify(PropertyDeveloperRequest sellerDashboardRequest) {

	    if (sellerDashboardRequest.getSellerName() == null || sellerDashboardRequest.getSellerName().isEmpty())
	        throw new IllegalArgumentException("Name is required");

	    if (String.valueOf(sellerDashboardRequest.getSellerMobileNumber()).length() != 10)
	        throw new IllegalArgumentException("Invalid mobile number");

//	    if (sellerDashboardRequest.getSellerEmail() == null)
//	        throw new IllegalArgumentException("Email is required");

	    if (sellerDashboardRequest.getSellerLocation() == null)
	        throw new IllegalArgumentException("Location needs to be filled");
	    
	    if (sellerDashboardRequest.getCost() <= 100000)
            throw new IllegalArgumentException("Please enter a valid property cost");
	    
	    PropertyType propertyType = sellerDashboardRequest.getPropertyType();
	    if (propertyType == null) {
	        throw new IllegalArgumentException("Mention the type of property");
	    }

	    if (propertyType != PropertyType.PLOTS && propertyType != PropertyType.LAND) {
	        if (sellerDashboardRequest.getTransactionType() == null)
	            throw new IllegalArgumentException("Transaction type needs to be filled");
	        if (sellerDashboardRequest.getConstructionStatus() == null)
	            throw new IllegalArgumentException("Please mention the construction status");
	        if (sellerDashboardRequest.getBhk() <= 0)
	            throw new IllegalArgumentException("The property should be at least 1BHK");
	        if (sellerDashboardRequest.getBathroom() <= 0)
	            throw new IllegalArgumentException("The property should have at least 1 bathroom");
	        if (sellerDashboardRequest.getFurnishedType() == null)
	            throw new IllegalArgumentException("Furnished type needs to be mentioned");
	        if (sellerDashboardRequest.getParking() == null)
	            throw new IllegalArgumentException("The parking type needs to be mentioned");
	        if (sellerDashboardRequest.getPossessionDate() == null)
	            throw new IllegalArgumentException("Need to mention the possession date");
	        if (sellerDashboardRequest.getBuiltUpArea() == 0)
	            throw new IllegalArgumentException("Built-up area is needed to register the property");
	        if (sellerDashboardRequest.getCarpetArea() == 0)
	            throw new IllegalArgumentException("Carpet area is needed to register the property");
	        if (sellerDashboardRequest.getPropertyAddress() == null || sellerDashboardRequest.getCity() == null
	                || sellerDashboardRequest.getPropertyLocation() == null || sellerDashboardRequest.getState() == null)
	            throw new IllegalArgumentException("Fields are missing, need to be filled");
	    }

	    if (sellerDashboardRequest.getArea() < 200)
	        throw new IllegalArgumentException("Enter the plot or land area, which should be more than 200 sq feet");
	    
	    if(propertyType== PropertyType.LAND && sellerDashboardRequest.getLandType()== null) {
	    	throw new IllegalArgumentException("Mention the land type");
	    }
	}

	

	@Override
	public ResponseEntity<ResponseStructure<PropertyDeveloperResponse>> addSellerWithProperty(
			PropertyDeveloperRequest sellerDashboardRequest, MultipartFile videoFile, MultipartFile photoFile) {
		verify(sellerDashboardRequest);

		PropertyDeveloper mapToEntity = mapToEntity(sellerDashboardRequest);

		// Verify the media getting from the frontend
		// try {
		// mediaVerification(photoFile, videoFile, mapToEntity);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }

		PropertyDeveloper sellerDashboard = sellerDashboardRepository.save(mapToEntity);
		PropertyDeveloperResponse sellerDashboardResponse = mapToResponse(sellerDashboard);

		return ResponseEntity.ok(responseStructure.setData(sellerDashboardResponse)
				.setMessage("Hey " + sellerDashboardRequest.getSellerName() + "you are added as a Seller")
				.setStatusCode(HttpStatus.OK.value()));
	}

	@Override
	public ResponseEntity<ResponseStructure<List<String>>> findLocationByCity(String city) {
		Optional<List<PropertyDeveloper>> optionalPropertyDevelopers = sellerDashboardRepository.findByCity(city);

		if (optionalPropertyDevelopers.isPresent()) {
			List<PropertyDeveloper> propertyDevelopers = optionalPropertyDevelopers.get();
			Set<String> citySet = new HashSet<>();

			for (PropertyDeveloper propertyDeveloper : propertyDevelopers) {
				String propertyLocation = propertyDeveloper.getPropertyLocation();
				if (!citySet.contains(propertyLocation)) {
					citySet.add(propertyLocation);
				}
			}

			// Convert set to list if needed
			List<String> cities = new ArrayList<>(citySet);

			ResponseStructure<List<String>> responseStructure = new ResponseStructure<>();
			responseStructure.setMessage("Locations fetched based on the city").setStatusCode(HttpStatus.OK.value())
					.setData(cities);

			return ResponseEntity.ok(responseStructure);
		} else {
			ResponseStructure<List<String>> responseStructure = new ResponseStructure<>();
			responseStructure.setMessage("No properties found in the provided location")
					.setStatusCode(HttpStatus.NOT_FOUND.value()).setData(Collections.emptyList());

			return ResponseEntity.ok(responseStructure);
		}
	}

	@Override
	public ResponseEntity<ResponseStructure<List<PropertyDeveloperResponse>>> findPropertyByLocation(String location) {
		Optional<List<PropertyDeveloper>> properties = sellerDashboardRepository.findByPropertyLocation(location);

		List<PropertyDeveloperResponse> collect = properties.orElse(Collections.emptyList()).stream()
				.map(property -> mapToResponse(property)).collect(Collectors.toList());

		ResponseStructure<List<PropertyDeveloperResponse>> responseStructure = new ResponseStructure<>();
		responseStructure.setData(collect);
		responseStructure.setMessage("The number of properties fetched is " + collect.size());
		responseStructure.setStatusCode(HttpStatus.OK.value());

		return ResponseEntity.ok(responseStructure);
	}
	// private void mediaVerification(MultipartFile photoFile, MultipartFile
	// videoFile, PropertyDeveloper sellerDashboard) throws IOException {
	// if (photoFile != null && !photoFile.isEmpty()) {
	// sellerDashboard.setVideo(videoFile.getBytes());
	// }
	// if (videoFile != null && !videoFile.isEmpty()) {
	// sellerDashboard.setPhoto(photoFile.getBytes());
	//
	// }
	// }
}
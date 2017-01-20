package com.nagifts;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.DateFormat;
import java.util.Date;

public class WebService {
	
	private static String NAMESPACE = "http://tempuri.org/";
	private static String URL = "http://23.91.115.57/nagifts/NaGiftsWebService.asmx";
	private static String SOAP_ACTION = "http://tempuri.org/";
	
	public static String CreteLogin(String mobile,String pasword, String webMethName) {
		String resTxt = null;
		// Create request
		SoapObject request = new SoapObject(NAMESPACE, webMethName);
		// Property which holds input parameters
		PropertyInfo celsiusPI = new PropertyInfo();
		// Set Name
		celsiusPI.setName("mobile");
		// Set Value
		celsiusPI.setValue(mobile);
		// Set dataType
		celsiusPI.setType(String.class);
		// Add the property to request object
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("pasword");
		celsiusPI.setValue(pasword);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		// Create envelope
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		// Set output SOAP object
		envelope.setOutputSoapObject(request);
		// Create HTTP call object
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			// Invole web service
			androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
			// Get the response
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			// Assign it to fahren static variable
			resTxt = response.toString();

		} catch (Exception e) {
			e.printStackTrace();
			resTxt = "No Network Found";
		} 
		
		return resTxt;
	}

	public static String CreteUser(String DelBoy_Fname, String DBoyMName, String DelBoy_Lname, String DelBoy_Contact, String Password, String DelBoy_Add, String DelBoy_Email,String Idproof,String photo,String userRole, String webMethName) {
		String resTxt = null;
		// Create request
		SoapObject request = new SoapObject(NAMESPACE, webMethName);

		PropertyInfo celsiusPI = new PropertyInfo();
		celsiusPI.setName("DelBoy_Fname");
		celsiusPI.setValue(DelBoy_Fname);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("DBoyMName");
		celsiusPI.setValue(DBoyMName);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("DelBoy_Lname");
		celsiusPI.setValue(DelBoy_Lname);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);



		celsiusPI=new PropertyInfo();
		celsiusPI.setName("DelBoy_Contact");
		celsiusPI.setValue(DelBoy_Contact);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("Password");
		celsiusPI.setValue(Password);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("DelBoy_Add");
		celsiusPI.setValue(DelBoy_Add);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("DelBoy_Email");
		celsiusPI.setValue(DelBoy_Email);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("Idproof");
		celsiusPI.setValue(Idproof);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("photo");
		celsiusPI.setValue(photo);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI.setName("Role");
		celsiusPI.setValue(userRole);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		// Create envelope
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		// Set output SOAP object
		envelope.setOutputSoapObject(request);
		// Create HTTP call object
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			// Invole web service
			androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
			// Get the response
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			// Assign it to fahren static variable
			resTxt = response.toString();

		} catch (Exception e) {
			e.printStackTrace();
			resTxt = "Error occured";
		}

		return resTxt;
	}
	public static String ShowGift(String GiftAssignmentParentID,String ExecutiveId,String webMethName) {
		String resTxt = null;
		// Create request
		SoapObject request = new SoapObject(NAMESPACE, webMethName);

		PropertyInfo celsiusPI = new PropertyInfo();
		celsiusPI.setName("GiftAssignmentParentID");
		celsiusPI.setValue(GiftAssignmentParentID);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI = new PropertyInfo();
		celsiusPI.setName("ExecutiveId");
		celsiusPI.setValue(ExecutiveId);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		// Create envelope
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		// Set output SOAP object
		envelope.setOutputSoapObject(request);
		// Create HTTP call object
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			// Invole web service
			androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
			// Get the response
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			// Assign it to fahren static variable
			resTxt = response.toString();

		} catch (Exception e) {
			e.printStackTrace();
			resTxt = "No Network Found";
		}

		return resTxt;
	}
	public static String InsertDelevery(String GiftDeliveryID,String Status,String ContactPersonName,String ContactPersonNo,String GiftAssignmentParentID, String webMethName) {
		String resTxt = null;
		// Create request
		SoapObject request = new SoapObject(NAMESPACE, webMethName);

		PropertyInfo celsiusPI = new PropertyInfo();
		celsiusPI.setName("DeliveryBoyID");
		celsiusPI.setValue(GiftDeliveryID);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("Status");
		celsiusPI.setValue(Status);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("ContactPersonName");
		celsiusPI.setValue(ContactPersonName);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("ContactPersonNo");
		celsiusPI.setValue(ContactPersonNo);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("GiftAssignmentParentID");
		celsiusPI.setValue(GiftAssignmentParentID);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);



		// Create envelope
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		// Set output SOAP object
		envelope.setOutputSoapObject(request);
		// Create HTTP call object
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			// Invole web service
			androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
			// Get the response
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			// Assign it to fahren static variable
			resTxt = response.toString();

		} catch (Exception e) {
			e.printStackTrace();
			resTxt = "No Network Found";
		}

		return resTxt;
	}
	public static String InsertSigniture(String GiftAssignmentParentID,String Signiture, String webMethName) {
		String resTxt = null;
		// Create request
		SoapObject request = new SoapObject(NAMESPACE, webMethName);

		PropertyInfo celsiusPI = new PropertyInfo();
		celsiusPI.setName("GiftAssignmentParentID");
		celsiusPI.setValue(GiftAssignmentParentID);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("Signiture");
		celsiusPI.setValue(Signiture);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		// Create envelope
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		// Set output SOAP object
		envelope.setOutputSoapObject(request);
		// Create HTTP call object
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			// Invole web service
			androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
			// Get the response
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			// Assign it to fahren static variable
			resTxt = response.toString();

		} catch (Exception e) {
			e.printStackTrace();
			resTxt = "No Network Found";
		}

		return resTxt;
	}
	public static String GetDistrict(String webMethName) {
		String resTxt = null;
		// Create request
		SoapObject request = new SoapObject(NAMESPACE, webMethName);

		// Create envelope
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		// Set output SOAP object
		envelope.setOutputSoapObject(request);
		// Create HTTP call object
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			// Invole web service
			androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
			// Get the response
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			// Assign it to fahren static variable
			resTxt = response.toString();

		} catch (Exception e) {
			e.printStackTrace();
			resTxt = "No Network Found";
		}

		return resTxt;
	}
	public static String CreateClient(String title,String fullname,String options,String Client_Contactno,String Client_Company,String addres,String email,String landmark,String location,String city,String district,String state,String pincode,String ClientTypeId, String webMethName) {
		String resTxt = null;
		// Create request
		SoapObject request = new SoapObject(NAMESPACE, webMethName);

		PropertyInfo celsiusPI = new PropertyInfo();
		celsiusPI.setName("title");
		celsiusPI.setValue(title);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("fullname");
		celsiusPI.setValue(fullname);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("options");
		celsiusPI.setValue(options);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("Client_Contactno");
		celsiusPI.setValue(Client_Contactno);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("Client_Company");
		celsiusPI.setValue(Client_Company);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("addres");
		celsiusPI.setValue(addres);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("email");
		celsiusPI.setValue(email);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("landmark");
		celsiusPI.setValue(landmark);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("location");
		celsiusPI.setValue(location);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("city");
		celsiusPI.setValue(city);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("district");
		celsiusPI.setValue(district);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("state");
		celsiusPI.setValue(state);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("pincode");
		celsiusPI.setValue(pincode);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("ClientTypeId");
		celsiusPI.setValue(ClientTypeId);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);


		// Create envelope
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		// Set output SOAP object
		envelope.setOutputSoapObject(request);
		// Create HTTP call object
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			// Invole web service
			androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
			// Get the response
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			// Assign it to fahren static variable
			resTxt = response.toString();

		} catch (Exception e) {
			e.printStackTrace();
			resTxt = "No Network Found";
		}

		return resTxt;
	}
	public static String Notiication(int currentPage, String webMethName) {
		String resTxt = null;
		// Create request
		SoapObject request = new SoapObject(NAMESPACE, webMethName);

		PropertyInfo celsiusPI = new PropertyInfo();
		celsiusPI.setName("currentPage");
		celsiusPI.setValue(currentPage);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		// Create envelope
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		// Set output SOAP object
		envelope.setOutputSoapObject(request);
		// Create HTTP call object
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			// Invole web service
			androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
			// Get the response
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			// Assign it to fahren static variable
			resTxt = response.toString();

		} catch (Exception e) {
			e.printStackTrace();
			resTxt = "No Network Found";
		}
		return resTxt;
	}

	public static String OldNotiication(int currentPage, String Date, String Name, String webMethName) {
		String resTxt = null;
		// Create request
		SoapObject request = new SoapObject(NAMESPACE, webMethName);

		PropertyInfo celsiusPI = new PropertyInfo();
		celsiusPI.setName("currentPage");
		celsiusPI.setValue(currentPage);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("Date");
		celsiusPI.setValue(Date);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("Name");
		celsiusPI.setValue(Name);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		// Create envelope
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		// Set output SOAP object
		envelope.setOutputSoapObject(request);
		// Create HTTP call object
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			// Invole web service
			androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
			// Get the response
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			// Assign it to fahren static variable
			resTxt = response.toString();

		} catch (Exception e) {
			e.printStackTrace();
			resTxt = "No Network Found";
		}
		return resTxt;
	}

	public static String FetchRole( String webMethName) {
		String resTxt = null;
		// Create request
		SoapObject request = new SoapObject(NAMESPACE, webMethName);

		// Create envelope
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		// Set output SOAP object
		envelope.setOutputSoapObject(request);
		// Create HTTP call object
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			// Invole web service
			androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
			// Get the response
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			// Assign it to fahren static variable
			resTxt = response.toString();

		} catch (Exception e) {
			e.printStackTrace();
			resTxt = "No Network Found";
		}
		return resTxt;
	}
}

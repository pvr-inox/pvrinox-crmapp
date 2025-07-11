package com.cinema.crm.modules.utils;

import java.io.StringReader;
import java.util.Date;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.cinema.crm.databases.pvrinox.entities.Cinema;
import com.cinema.crm.databases.pvrinox.entities.Pos;
import com.cinema.crm.databases.pvrinox.entities.SBTransactions;
import com.cinema.crm.databases.pvrinox.repositories.SBTransactionRepository;
import com.cinema.crm.modules.model.OrderCancelData;
import com.cinema.crm.modules.model.OrderCommitData;
import com.cinema.crm.modules.model.ReqRefundTrans;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class ShowbizUtil {
	private final SoapClient soapClient;
	private final SBTransactionRepository sbTransactionRepository;
	private final Utilities utilities;
	
	 private final String envlopeHeader = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
	            "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
	            "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" " +
	            "xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">" +
	            "<soap12:Body>";
	    private final String envlopeFooter = "</soap12:Body>" +
	            "</soap12:Envelope>";
	    
	 public ShowbizUtil(SoapClient soapClient, SBTransactionRepository sbTransactionRepository,Utilities utilities) {
			super();
			this.soapClient = soapClient;
			this.sbTransactionRepository = sbTransactionRepository;
			this.utilities =utilities;
		}


	public String soldStatus(Cinema cinema, Pos pos, String platform, String chain, String transid) {
	        try {
	            String soapRequest = envlopeHeader +
	                    "<FnSchedule_SoldStatus xmlns=\"http://tempuri.org/\">" +
	                    "      <TheatreId>" + cinema.getTheatreId() + "</TheatreId>" +
	                    "      <PartnerId>" + pos.getShowBizPartner().get(platform + "-" + chain).getPartnerId() + "</PartnerId>" +
	                    "      <PartnerPwd>" + pos.getShowBizPartner().get(platform + "-" + chain).getPass() + "</PartnerPwd>" +
	                    "      <UniqueRequestId>" + transid + "</UniqueRequestId>" +
	                    "    </FnSchedule_SoldStatus>" + envlopeFooter;
	            log.info("SOLD STATUS ::: " + pos.getChainKey() + " :: " + pos.getName() + " :: URL :: "
	                    + pos.getShowBizContentUrl() + "  :: Request :: " + soapRequest);
	            SBTransactions sbTransactions = new SBTransactions(platform, chain, cinema.getTheatreId(),
	                    "SOLDSTATUS", soapRequest, new Date()
	                    ,Long.parseLong(transid),utilities.createBookingId(cinema.getTheatreId(), Integer.parseInt(transid)));
	            String response = soapClient.sendSoapRequest("http://192.168.200.67/Schedule_HO/Schedule_service.asmx", soapRequest);
	            log.info("SOLD STATUS ::: " + pos.getChainKey() + " :: " + pos.getName() + " :: Response :: " + response);
	            sbTransactions.setResponseTime(new Date());
	            sbTransactions.setResponse(response);
	            sbTransactions.setTimeTaken((int)utilities.getTimeDiffInSeconds(sbTransactions.getRequestTime(),sbTransactions.getResponseTime()));
	            sbTransactionRepository.save(sbTransactions);
	            return response;
	        } catch (Exception e) {
	        	log.error(e);
	        }
	        return null;
	    }
	
	 
	 public String soldStatusFb(Cinema cinema, Pos pos, String platform, String chain, String transid, String foodType) {
	        try {
	            String soapRequest = envlopeHeader +
	                    "<FnSchedule_FB_SoldStatus xmlns=\"http://tempuri.org/\">" +
	                    "      <TheatreId>" + cinema.getTheatreId() + "</TheatreId>" +
	                    "      <PartnerId>" + pos.getShowBizPartner().get(platform + "-" + chain).getPartnerId() + "</PartnerId>" +
	                    "      <PartnerPwd>" + pos.getShowBizPartner().get(platform + "-" + chain).getPass() + "</PartnerPwd>" +
	                    "      <UniqueRequestId>" + transid + "</UniqueRequestId>" +
	                    "      <TransactionType>" + foodType + "</TransactionType>" +
	                    "    </FnSchedule_FB_SoldStatus>" + envlopeFooter;
	            log.info("SOLD STATUS FB::: " + pos.getChainKey() + " :: " + pos.getName() + " :: URL :: "
	                    + pos.getShowBizContentUrl() + "  :: Request :: " + soapRequest);
	            SBTransactions sbTransactions = new SBTransactions(platform, chain, cinema.getTheatreId(),
	                    "SOLDSTATUSFB", soapRequest, new Date()
	                    ,Long.parseLong(transid),utilities.createBookingId(cinema.getTheatreId(), Integer.parseInt(transid)));
	            String response = soapClient.sendSoapRequest("http://192.168.200.67/Schedule_HO/Schedule_service.asmx", soapRequest);
	            log.info("SOLD STATUS FB::: " + pos.getChainKey() + " :: " + pos.getName() + " :: Response :: " + response);
	            sbTransactions.setResponseTime(new Date());
	            sbTransactions.setResponse(response);
	            sbTransactions.setTimeTaken((int)utilities.getTimeDiffInSeconds(sbTransactions.getRequestTime(),sbTransactions.getResponseTime()));
	            sbTransactionRepository.save(sbTransactions);
	            return response;
	        } catch (Exception e) {
	        	log.error(e);
	        }
	        return null;
	    }
	 
	 
	 public String cancelBuy(Cinema cinema, Pos pos, ReqRefundTrans request) {
	        try {
	            Random random = new Random();
	            String soapRequest = envlopeHeader +
	                    " <CancelBuy xmlns=\"http://tempuri.org/\">" +
	                    "      <TheatreId>" + cinema.getTheatreId() + "</TheatreId>" +
	                    "      <PartnerId>" + pos.getShowBizPartner().get(request.getPlatform() + "-" + request.getChain()).getPartnerId() + "</PartnerId>" +
	                    "      <PartnerPwd>" + pos.getShowBizPartner().get(request.getPlatform() + "-" + request.getChain()).getPass() + "</PartnerPwd>" +
	                    "      <PaidUniqueRequestId>" + request.getBookId() + "</PaidUniqueRequestId>" +
	                    "      <PaidReceiptNo>" + request.getReceiptNo() + "</PaidReceiptNo>" +
	                    "      <UniqueRequestId>" + request.getBookId() + random.nextInt(90) + "</UniqueRequestId>" +
	                    "      <Remarks>" + request.getRemarks() + "</Remarks>" +
	                    "      <Show_Date>" + request.getShowDate() + "</Show_Date>" +
	                    "      <Show_Time>" + request.getShowTime() + "</Show_Time>" +
	                    "      <Screen_Id>" + request.getScreenId() + "</Screen_Id>" +
	                    "      <Rate>" + request.getRate() + "</Rate>" +
	                    "      <NoOfTicket>" + request.getNoOfTicket() + "</NoOfTicket>" +
	                    "      <Value>" + request.getValue() + "</Value>" +
	                    "      <FBNoOfItems>" + request.getFbNoOfItems() + "</FBNoOfItems>" +
	                    "      <FBValue>" + request.getFbValue() + "</FBValue>" +
	                    "      <PGId>" + request.getPgId() + "</PGId>" +
	                    "      <TransactionType>" + request.getTransType() + "</TransactionType>" +
	                    "      <JobType>2</JobType>" +
	                    "    </CancelBuy>" + envlopeFooter;
	            log.info("CANCEL BUY ::: " + pos.getChainKey() + " :: " + pos.getName() + " :: URL :: "
	                    + pos.getShowBizBookingUrl() + "  :: Request :: " + soapRequest);
	            SBTransactions sbTransactions = new SBTransactions(request.getPlatform(), request.getChain(), request.getCcode(),
	                    "CANCELBUY", soapRequest, new Date()
	                    ,Long.parseLong(request.getBookId()),utilities.createBookingId(request.getCcode(), Integer.parseInt(request.getBookId())));
	            String response = soapClient.sendSoapRequest("http://192.168.200.67/SeatService_HO/SeatBook.asmx", soapRequest);
	            log.info("CANCEL BUY ::: " + pos.getChainKey() + " :: " + pos.getName() + " :: Response :: " + response);
	            sbTransactions.setResponseTime(new Date());
	            sbTransactions.setResponse(response);
	            sbTransactions.setTimeTaken((int)utilities.getTimeDiffInSeconds(sbTransactions.getRequestTime(),sbTransactions.getResponseTime()));
	            sbTransactionRepository.save(sbTransactions);
	            return response;
	        } catch (Exception e) {
	        	log.error(utilities.error(e));
	        }
	        return null;
	    }
	 
	 public OrderCommitData getShowBizOrderDataVerify(String response) throws Exception {
	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder builder = factory.newDocumentBuilder();
	        InputSource is = new InputSource(new StringReader(response));
	        Document document = builder.parse(is);
	        // Get the root element
	        Element root = document.getDocumentElement();
	        int statusId = Integer.parseInt(getElementValue(root, "StatusId"));
	        String uniqueRequestId = getElementValue(root, "UniqueRequestId");
	        String unpaidSource = getElementValue(root,"UnpaidToPaidPaySource");

	        String errorMessage = getElementValue(root, "StatusMessage");
	        OrderCommitData orderData = new OrderCommitData();
	        orderData.setUnpaidToPaidPaySource(unpaidSource);
	        orderData.setStatusId(statusId);
	        if (statusId == 2 || statusId == 3 || (!ObjectUtils.isEmpty(unpaidSource) && statusId==7)) {
	            orderData.setSuccess(true);
	            orderData.setErrorMsg(errorMessage);
	            orderData.setCinemaId(getElementValue(root, "TheatreId"));
	            orderData.setPos("SHOWBIZ");
	            orderData.setBookingId(getElementValue(root, "ReceiptNo"));
	            orderData.setItemBookingId(getElementValue(root, "ItemReceiptNo"));
	            orderData.setGstInvoiceNumber(getElementValue(root, "ItemGSTInvoiceNo"));
	            orderData.setCancelStatus(getElementValue(root, "CancellationStatus"));
	            if (!ObjectUtils.isEmpty(orderData.getCancelStatus())) {
	                if (orderData.getCancelStatus().equalsIgnoreCase("Cancelled")) {
//	                orderData.setCanChargePercent((long) (Double.parseDouble(getElementValue(root, "CanChargePercent")) * 100));
	                    orderData.setBasicCanCharge((long) (Double.parseDouble(getElementValue(root, "BasicCanCharge")) * 100));
	                    orderData.setCanChgCGST((long) (Double.parseDouble(getElementValue(root, "CanChgCGST")) * 100));
	                    orderData.setCanChgSGST((long) (Double.parseDouble(getElementValue(root, "CanChgSGST")) * 100));
	                    orderData.setCanChgUTGST((long) (Double.parseDouble(getElementValue(root, "CanChgUTGST")) * 100));
	                    orderData.setCanChgOtherTax((long) (Double.parseDouble(getElementValue(root, "CanChgOtherTax")) * 100));
	                    orderData.setTotalCanCharge((long) (Double.parseDouble(getElementValue(root, "TotalCanCharge")) * 100));
	                    orderData.setRefundAmount((long) (Double.parseDouble(getElementValue(root, "RefundAmount")) * 100));
//	                orderData.setTicketRefundPercent((long) (Double.parseDouble(getElementValue(root, "TicketRefundPercent")) * 100));
	                }
	            }
	            NodeList ticketDetailsList = root.getElementsByTagName("Seat");
	            OrderCommitData.SBTickets ticket = null;
	            for (int i = 0; i < ticketDetailsList.getLength(); i++) {
	                Element ticketDetails = (Element) ticketDetailsList.item(i);
	                ticket = new OrderCommitData.SBTickets();
	                ticket.setGstInvoiceNo(getElementValue(ticketDetails, "GSTInvoiceNo"));
	                if (!ObjectUtils.isEmpty(orderData.getCancelStatus())) {
	                    if (orderData.getCancelStatus().equalsIgnoreCase("Cancelled")) {
	                        ticket.setGstCNNo(getElementValue(ticketDetails, "GSTCNNo"));
	                    }
	                }
	                try {
	                	if(!ObjectUtils.isEmpty(getElementValue(ticketDetails, "Row"))) {
	                		ticket.setRowNo(getElementValue(ticketDetails, "Row"));
	                	}
	                	if(!ObjectUtils.isEmpty(getElementValue(ticketDetails, "Col"))) {
	                		ticket.setSeatNo(getElementValue(ticketDetails, "Col"));
	                	}
	                } catch (Exception ex) {
	                	log.info("EXCEPTION IN SETTING ROW & SEAT :: {} :: {}", uniqueRequestId, utilities.error(ex));
	                }
	                orderData.getTickets().add(ticket);
	            }
	        } else {
	            orderData.setSuccess(false);
	            orderData.setErrorMsg(errorMessage);
	        }
	        return orderData;
	    }
	 
	 public OrderCommitData getShowBizOrderDataVerifyFood(String response) throws Exception {
	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder builder = factory.newDocumentBuilder();
	        InputSource is = new InputSource(new StringReader(response));
	        Document document = builder.parse(is);
	        // Get the root element
	        Element root = document.getDocumentElement();
	        int statusId = Integer.parseInt(getElementValue(root, "StatusId"));
	        String uniqueRequestId = getElementValue(root, "UniqueRequestId");

	        String errorMessage = getElementValue(root, "StatusMessage");
	        OrderCommitData orderData = new OrderCommitData();
	        orderData.setStatusId(statusId);
	        if (statusId == 2) {
	            orderData.setSuccess(true);
	            orderData.setErrorMsg(errorMessage);
	            orderData.setCinemaId(getElementValue(root, "TheatreId"));
	            orderData.setPos("SHOWBIZ");
	            orderData.setItemBookingId(getElementValue(root, "ItemReceiptNo"));
	            orderData.setGstInvoiceNumber(getElementValue(root, "ItemGSTInvoiceNo"));
	        } else {
	            orderData.setSuccess(false);
	            orderData.setErrorMsg(errorMessage);
	        }
	        return orderData;
	    }
	 
	 public OrderCancelData getShowBizCancelBuyData(String response, Cinema cinema, ReqRefundTrans request) throws Exception {
	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder builder = factory.newDocumentBuilder();
	        InputSource is = new InputSource(new StringReader(response));
	        Document document = builder.parse(is);
	        // Get the root element
	        Element root = document.getDocumentElement();
	        int statusId = Integer.parseInt(getElementValue(root, "StatusId"));
	        String uniqueRequestId = getElementValue(root, "UniqueRequestId");
	        String errorMessage = getElementValue(root, "ErrMessage");
	        OrderCancelData orderData = new OrderCancelData();
	        if (statusId == 1 || statusId == 2) {
	            orderData.setSuccess(true);
	            orderData.setStatusId(statusId);
	            orderData.setCinemaId(getElementValue(root, "TheatreId"));
	            orderData.setChain(cinema.getChainKey());
	            orderData.setChain(cinema.getPos());
	            orderData.setCanChargePercent((long) (Double.parseDouble(getElementValue(root, "CanChargePercent")) * 100));
	            orderData.setBasicCanCharge((long) (Double.parseDouble(getElementValue(root, "BasicCanCharge")) * 100));
	            orderData.setCanChgCGST((long) (Double.parseDouble(getElementValue(root, "CanChgCGST")) * 100));
	            orderData.setCanChgSGST((long) (Double.parseDouble(getElementValue(root, "CanChgSGST")) * 100));
	            orderData.setCanChgUTGST((long) (Double.parseDouble(getElementValue(root, "CanChgUTGST")) * 100));
	            orderData.setCanChgOtherTax((long) (Double.parseDouble(getElementValue(root, "CanChgOtherTax")) * 100));
	            orderData.setTotalCanCharge((long) (Double.parseDouble(getElementValue(root, "TotalCanCharge")) * 100));
	            orderData.setRefundAmount((long) (Double.parseDouble(getElementValue(root, "RefundAmount")) * 100));
	            orderData.setTicketRefundPercent((long) (Double.parseDouble(getElementValue(root, "TicketRefundPercent")) * 100));

	            NodeList ticketDetailsList = root.getElementsByTagName("TicketDetails");
	            OrderCancelData.SBTickets ticket = null;
	            for (int i = 0; i < ticketDetailsList.getLength(); i++) {
	                Element ticketDetails = (Element) ticketDetailsList.item(i);
	                ticket = new OrderCancelData.SBTickets();
	                ticket.setTicketNo(getElementValue(ticketDetails, "TicketNo"));
	                ticket.setRowNo(getElementValue(ticketDetails, "RowNo"));
	                ticket.setSeatNo(getElementValue(ticketDetails, "SeatNo"));
	                ticket.setRate(getElementValue(ticketDetails, "Rate"));
	                ticket.setGstCNNo(getElementValue(ticketDetails, "GSTCNNo"));
	                ticket.setTicketPrice(getElementValue(ticketDetails, "Nett"));
	                ticket.setCgst(getElementValue(ticketDetails, "CGST"));
	                ticket.setSgst(getElementValue(ticketDetails, "SGST"));
	                ticket.setUtgst(getElementValue(ticketDetails, "UTGST"));
	                orderData.getTickets().add(ticket);
	            }
	        } else {
	            orderData.setSuccess(false);
	            orderData.setStatusId(statusId);
	            orderData.setErrorMsg(errorMessage);

	        }
	        return orderData;
	    }
	 
	 private static String getElementValue(Element parentElement, String tagName) {
	        NodeList nodeList = parentElement.getElementsByTagName(tagName);
	        if (nodeList.getLength() > 0) {
	            Node node = nodeList.item(0);
	            return node.getTextContent();
	        }
	        return null;
	    }
	

}

package com.project2.projecttwo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.python.antlr.PythonParser.else_clause_return;
import org.python.core.JythonInitializer;
import org.python.core.PyFunction;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectSerializer;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project2.projecttwo.entity.AnalyzedUser;
import com.project2.projecttwo.entity.Customer;
import com.project2.projecttwo.entity.User;
import com.project2.projecttwo.esewaapi.ApiSecurityExample;
import com.project2.projecttwo.service.CustService;
import com.project2.projecttwo.service.Service;
import com.project2.projecttwo.service.Analyzedservice;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @Autowired
    private Service service;
    @Autowired
    private CustService cuservice;
    @Autowired
    private Analyzedservice auservice;

    @RequestMapping("/")
    public String Startup() {
        return "startup";
    }

    @RequestMapping("/login")
    public String login() {
        return "index";
    }

    @RequestMapping("/home")
    public String home(HttpServletRequest request, Model m, RedirectAttributes redirectAttributes,
            HttpSession session) {
        String email = request.getParameter("loginemail");
        String password = request.getParameter("loginpassword");
        int pass = password.length();
        if ((pass > 5 && pass < 15)) {
            Optional<User> optionaluser = service.IsEmailPresent(email);
            if (optionaluser.isPresent()) {
                User us = optionaluser.get();
                // System.out.println(us.toString());
                if (us.getConPassword().equals(password)) {

                    redirectAttributes.addFlashAttribute("email", us.getRegEmail());
                    if (us.getCatageory() == null) {
                        // m.addAttribute("email",us.getRegEmail() );

                        // System.out.println(us.getCatageory());
                        session.setAttribute("Customer", "Customer");
                        session.setAttribute("Customeremail", us.getRegEmail());

                        return "redirect:/userpayment";
                    } else {
                        if ((us.getCatageory().equals("on"))) {
                            session.setAttribute("Admin", "Admin");
                            session.setAttribute("Adminemail", us.getRegEmail());
                            System.out.println(us.getCatageory());
                            System.out.println("Inside admin mapping");

                            return "redirect:/admin";
                        } else if (us.getCatageory().equals("analyst")) {
                            session.setAttribute("analyst", "analyst");
                            session.setAttribute("analystacess", "analystacess");

                            session.setAttribute("analystemail", us.getRegEmail());
                            return "redirect:/CustomerRequest";
                        } else {
                            session.setAttribute("Customer", "Customer");
                            session.setAttribute("Customeremail", us.getRegEmail());

                            return "redirect:/userpayment";
                        }

                    }

                } else
                    System.out.println("Password Doesnt Match");
            } else
                System.out.println("Email Address doesn't Found");

        } else {
            System.out.println("Invalid Password");
        }
        return "redirect:/";
    }

    @RequestMapping("/registration")
    public String registration() {

        return "Registration";
    }

    @RequestMapping("/register")
    public String register(@ModelAttribute User user) {

        Optional<User> optionaluser = service.IsEmailPresent(user.getRegEmail());
        if (!optionaluser.isPresent()) {
            if (user.getConPassword().equals(user.getRegPassword())) {

                service.saveDetails(user);
                System.out.println("Data is Stored");
                String name = user.getRegName();
                service.sendEmail(user.getRegEmail(), "Registration Completed",
                        "HI" + name + "Welcome To Bussiness Analyzer.You are sucessfuly registered!!");

                return "redirect:/";

            } else {
                return "redirect:/registration";
            }

        } else {
            System.out.println("Email is already Exists");
            // return "redirect:/registration";
            return "redirect:/registration";
        }
    }

    @RequestMapping("/gotologin")
    public String requestMethodName() {
        return "redirect:/login";
    }

    @PostMapping("/report")
    public String getMethodName(
            @RequestParam("file") MultipartFile file,
            @RequestParam("bussiness") String bussiness,
            @RequestParam("message") String message,
            HttpSession session, Model m, RedirectAttributes redirectAttributes) throws IOException {

        String email = (String) session.getAttribute("useremail");
        if (email == null) {
            return "redirect:/";
        }
        Optional<Customer> existingCustomer = cuservice.findEmailCustomer(email);
        Customer cu = existingCustomer.get();
        cu.setMessage(message);
        cu.setFilename(file.getOriginalFilename());
        // finding name through email of another entity class (or from another table)
        Optional<User> optionalUser = service.IsEmailPresent(email);
        System.out.println(optionalUser);
        if (optionalUser.isPresent()) {
            User us = optionalUser.get();
            cu.setName(us.getRegName());
            cu.setCatageory(us.getCatageory());

        }

        try {
            byte[] fileBytes = file.getBytes();
            cu.setFile(fileBytes);
        } catch (IOException e) {
            System.out.println("Exception Found");
            e.printStackTrace();
        }

        if (cu.getBussiness() == null) {
            cu.setBussiness(bussiness);
            cu.setStatus("unanalyzed");
            System.out.println("Inside null business");
            cuservice.saveCustDetails(cu);
            System.out.println(cu.getStatus());
            m.addAttribute("usersDetails", cu);

            return "report";
        } else {
            System.out.println("Email is present");
            redirectAttributes.addFlashAttribute("alreadyDataSubmited", "alreadyDataSubmited");

            return "redirect:/custlogin";
        }
    }

    @RequestMapping("/custlogin")
    public String custlogin(HttpSession session) {
        String customer = (String) session.getAttribute("Customer");

        if (customer != null && customer.equals("Customer")) {

            String email = (String) session.getAttribute("Customeremail");
            if (email == null) {
                System.out.println("Email not found");
            } else {
                session.setAttribute("useremail", email);
            }
            System.out.println(email);

            return "custlogin";
        } else {
            return "redirect:/";
        }
    }

    @RequestMapping("/admin")
    public String AdminPage(Customer cu, User us, Model m,HttpSession session) {
        String admin= (String)session.getAttribute("Admin");
        if(admin!=null &&admin.equals("Admin"))
        {
        List<Customer> li = cuservice.getCustomerDetails();
        int total_register = li.size();
        List<User> lu = service.getUsersWithNullCategory();
        // System.out.println(lu);
        m.addAttribute("total_register", total_register);
        m.addAttribute("unregisteredUser", lu);
        m.addAttribute("registeredUser", li);
        m.addAttribute("total_unregister", lu.size());
        m.addAttribute("total_user", total_register + lu.size());

        List<AnalyzedUser> an = auservice.getDetails();
        m.addAttribute("analyzeduser", an);

        // System.out.println(total_register);

        return "Admin";
        }
        else
        {
            return "redirect:/";
        }
    }

    @RequestMapping("/users")
    public String UsersPage(Model m) {
        m.addAttribute("usersDetails", service.GetDetails());
        return "Users";
    }

    @RequestMapping("/analyst")
    public String AnalystPage(Model m, User us) {
        List<User> li = service.getAnalyst();
        m.addAttribute("analystList", li);
        return "Analyst";
    }

    @GetMapping("/deleteUsers/{id}")
    public String deleteUsers(@PathVariable int id, User us, Customer cu) {
        Optional<User> user = service.findbyid(id);
        us = user.get();
        String email = us.getRegEmail();
        service.deletebyid(id);
        if (cuservice.findEmailCustomer(email).isPresent()) {
            Optional<Customer> customer = cuservice.findEmailCustomer(email);
            cu = customer.get();
            cuservice.deleterecord(cu.getSn());
        }

        return "redirect:/users";
    }

    @RequestMapping("/addAnalyst")
    public String AddAnalyst(@RequestParam("name") String name, @RequestParam("email") String email, User us) {
        us.setRegEmail(email);
        us.setRegName(name);
        us.setRegPassword("Analyst@123");
        us.setConPassword("Analyst@123");
        us.setCatageory("analyst");
        System.out.println(us);
        service.saveDetails(us);
        return "redirect:/admin";
    }

    @RequestMapping("/user_report")
    public String User_Report(HttpSession session, Customer cu, Model m) {
        String email = (String) session.getAttribute("Customeremail");
        Optional<Customer> os = cuservice.findEmailCustomer(email);
        if (os.isEmpty()) {
            m.addAttribute("emptyvalue", "emptyvalue");
            return "report";

        } else {
            cu = os.get();
            System.out.println(cu);
            String editvalue = (String) session.getAttribute("edit");
            if (editvalue != null) {
                m.addAttribute("edit", "edit");
                System.out.println("value is present");
                session.removeAttribute("edit");

            } else {
                System.out.println("value is null");
            }
            m.addAttribute("usersDetails", cu);
            if (cu.getReportfile() != null) {
                m.addAttribute("reportfile", "reportfile");
                m.addAttribute("message", cu.getAnalystmessage());
                System.out.println(cu.getAnalystmessage());
            }
            return "report";
        }
    }

    @RequestMapping("/CustomerRequest")
    public String requestMethodName(Customer cu, Model m, RedirectAttributes ra,HttpSession session) {
        String analyst=(String)session.getAttribute("analystacess");
        if(analyst==null)
        {
            return "redirect:/";
        }
        if(analyst.equals("analystacess"))
        {
        List<Customer> customers = cuservice.getCustomerDetails();
        // List<Customer> cuu = cuservice.getUserByStatus("unanalyzed");
        m.addAttribute("AllCustomers", customers);
        Integer id = (Integer) ra.getFlashAttributes().get("filepresent");
        String invalidanalyst = (String) ra.getFlashAttributes().get("invalidanalyst");

        String invalidanalystaccess = (String) ra.getFlashAttributes().get("invalidanalystaccess");

        System.out.println(id);
        if (id == null) {

        } else {
            m.addAttribute("filepresent", id);
            m.addAttribute("invalidanalyst", invalidanalyst);
            m.addAttribute("invalidanalystaccess", invalidanalystaccess);

        }
        return "CustomerRequest";
    }
    else{
        return "redirect:/";
    }
    }

    @GetMapping("/upload/{id}")
    public String uploadfile(@PathVariable int id, Customer cu, Model m, RedirectAttributes ra, HttpSession session)
            throws IOException {
        Optional<Customer> customer = cuservice.findbyid(id);
        String email = (String) session.getAttribute("analystemail");

        cu = customer.get();
        if (cu.getAnalyzedby() != null) {
            if (cu.getAnalyzedby().equals(email)) {
                cu.setStatus("Analyzed");
                cuservice.saveCustDetails(cu);
                if (cu.getReportfile() != null) {
                    ra.addFlashAttribute("filepresent", id);

                } else {
                    session.setAttribute("customerupload", cu);
                    ra.addFlashAttribute("uploadreport", true);
                    System.out.println("here");
                }
            } else {
                ra.addFlashAttribute("invalidanalyst", "invalidanalyst");
                ra.addFlashAttribute("filepresent", id);

            }
        }

        return "redirect:/CustomerRequest";
    }

    @PostMapping("/uploadfile")
    public String UploadFile(@RequestParam("uploadfile") MultipartFile file,
            @RequestParam("textarea") String message,
            RedirectAttributes rd, Customer cu, HttpSession session, AnalyzedUser au) throws IOException {
        cu = (Customer) session.getAttribute("customerupload");

        if (file.isEmpty()) {
            System.out.println("File is Empty");
        }
        byte[] reportfile = file.getBytes();
        cu.setReportfile(reportfile);
        cu.setReportname(file.getOriginalFilename());
        cu.setAnalystmessage(message);
        cuservice.saveCustDetails(cu);
        au.setRegEmail((cu.getEmail()));
        au.setRegName(cu.getName());
        auservice.savedetails(au);

        String msg = "Dear Customer " + cu.getName()
                + ", Your Data is analyzed and ready to view,we are seeking your both positive and negative feedback :) -BusinessAnalyzer ";
        service.sendEmail(cu.getEmail(), "Data Analyzed", msg);
        System.out.println(cu);
        return "redirect:/CustomerRequest";

    }

    @GetMapping("/showreport/{id}")
    public ResponseEntity<byte[]> getReportFile(@PathVariable int id, Customer cu, HttpSession session) {
        System.out.println(id);
        String email = (String) session.getAttribute("Customeremail");
        Optional<Customer> customer = cuservice.findEmailCustomer(email);
        cu = customer.get();

        byte[] pdfFile = cu.getReportfile();

        if (pdfFile != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(ContentDisposition.inline().filename("report.pdf").build());
            headers.setContentLength(pdfFile.length);
            System.out.println("inside this pdf vieweer");
            System.out.println(headers);
            System.out.println(new ResponseEntity<>(pdfFile, headers, HttpStatus.OK));
            return new ResponseEntity<>(pdfFile, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/download/{id}/file")
    public String downloadFile(@PathVariable int id, HttpSession session, RedirectAttributes ra, Customer customer)
            throws FileNotFoundException, IOException {
        String email = (String) session.getAttribute("analystemail");
        Optional<Customer> cu = cuservice.downloadfile(id);
        customer = cu.get();
        if (customer.getAnalyzedby() == null ) {
            customer.setAnalyzedby(email);
            byte[] filedata = customer.getFile();
            if (filedata != null) {
                System.out.println("herer");
                String desktopPath = System.getProperty("user.home") + File.separator + "Desktop/Business";
                String filePath = desktopPath + File.separator + customer.getFilename();
                System.out.println(desktopPath + filePath);

                File file = new File(filePath);
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    customer.setStatus("analyzing");
                    fos.write(filedata);
                    cuservice.saveCustDetails(customer);
                    System.out.println(customer.getStatus());

                }
                return "redirect:/CustomerRequest";

            }
        } else {
            
            ra.addFlashAttribute("invalidanalystaccess", "invalidanalystaccess");
            return "redirect:/CustomerRequest";
        }

        return "redirect:/";
    }

    @GetMapping("/signout")
    public String SignOut(HttpSession session) {

        session.removeAttribute("Customer");
        session.removeAttribute("Admin");
        return "redirect:/custlogin";
    }

    @GetMapping("/payviaesewa")
    public String redirectView(@RequestParam("stringParam") String category, Model m)
            throws InvalidKeyException, NoSuchAlgorithmException {
        ApiSecurityExample ap = new ApiSecurityExample();
        String transaction_uuid = UUID.randomUUID().toString();

        m.addAttribute("transaction_uuid", transaction_uuid);
        if (category.equals("average")) {
            m.addAttribute("category", "average");
            m.addAttribute("signature", ap.performpayment("25000", transaction_uuid));
            System.out.println(ap.performpayment("25000", transaction_uuid));

        } else if (category.equals("advance")) {
            m.addAttribute("category", "advance");
            m.addAttribute("signature", ap.performpayment("45000", transaction_uuid));

        } else {
            m.addAttribute("category", "basic");
            m.addAttribute("signature", ap.performpayment("15000", transaction_uuid));

        }

        // redirectview.setUrl("https://rc-epay.esewa.com.np/api/epay/main/v2/form");

        return "esewa";

    }

    @PostMapping("/payment")
    public String getPayment() {
        System.out.println("hiiii this is post payment");
        return "/report";
    }

    @GetMapping("/payment")
    public String PostPayment() {
        System.out.println("hiiii");
        return "/report";
    }

    @GetMapping("/analyze/{id}")
    public String AnalyzeData(@PathVariable int id) {

        Optional<Customer> cu = cuservice.downloadfile(id);
        Customer customer = cu.get();
        byte[] filedata = customer.getFile();
        System.out.println(filedata);
        if (filedata != null) {
            System.out.println("herer");
            String desktopPath = System.getProperty("user.home") + File.separator + "Desktop/Business";
            String originalFilename = customer.getFilename();
            String newFilename = "report" + originalFilename.substring(originalFilename.lastIndexOf('.'));
            String filePath = desktopPath + File.separator + newFilename;
            System.out.println(desktopPath + filePath);

            File file = new File(filePath);
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(filedata);

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return "redirect:/runPython";

        }
        return "redirect:/user_report";
    }

    @GetMapping("/userpayment")
    public String UserPayment(HttpSession session, Customer cu) throws IOException {
        String email = (String) session.getAttribute("Customeremail");
        System.out.println(email);
        Optional<Customer> customer = cuservice.findEmailCustomer(email);
        System.out.println(customer);
        if (!(customer.isEmpty())) {
            cu = customer.get();
            System.out.println(cu.getCatageory());
            if (cu.getCatageory() == null) {
                System.out.println("");
                return "payment";
            } else
                return "redirect:/custlogin";

        } else {
            cu.setEmail(email);

            cuservice.saveCustDetails(cu);

            System.out.println(cu.toString());
            Optional<Customer> customerone = cuservice.findEmailCustomer(email);

            cu = customerone.get();
            if (cu.getCatageory() == null) {
                return "payment";
            } else {
                return "redirect:/custlogin";
            }
        }

    }

    @GetMapping("/gateway")
    public String getMethodName(@RequestParam(value = "data", required = false) String base64Response,
            HttpSession session, User us) {
        try {
            // Decode the Base64 encoded response
            byte[] decodedBytes = Base64.getDecoder().decode(base64Response);
            String decodedResponse = new String(decodedBytes);

            // Parse the JSON response
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> responseMap = objectMapper.readValue(decodedResponse, Map.class);
            System.out.println(responseMap);

            // Extract individual fields (if needed)
            String transactionCode = (String) responseMap.get("transaction_code");
            String status = (String) responseMap.get("status");
            String totalAmount = (String) responseMap.get("total_amount");
            String transactionUuid = (String) responseMap.get("transaction_uuid");
            String productCode = (String) responseMap.get("product_code");
            String signedFieldNames = (String) responseMap.get("signed_field_names");
            String signature = (String) responseMap.get("signature");

            String email = (String) session.getAttribute("Customeremail");
            Optional<Customer> cu = cuservice.findEmailCustomer(email);
            Optional<User> user = service.IsEmailPresent(email);
            us = user.get();
            Customer customer = cu.get();
            customer.setName(us.getRegName());
            System.out.println(totalAmount);
            if ("15,000.0".equals(totalAmount)) {
                customer.setCatageory("basic");
                us.setCatageory("basic");

            } else if ("25,000.0".equals(totalAmount)) {
                customer.setCatageory("average");
                us.setCatageory("average");
            } else {
                customer.setCatageory("advance");
                us.setCatageory("advance");
            }
            cuservice.saveCustDetails(customer);
            service.saveDetails(us);

            return "redirect:/custlogin";

        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred while processing the payment response.";
        }
        // System.out.println(totalAmount);

    }

    @GetMapping("/delete/{id}")
    public String deleterecord(@PathVariable int id, Model m) throws IOException {
        Optional<Customer> cu = cuservice.findbyid(id);
        Customer cus = cu.get();
        cus.setFile(null);
        cus.setFilename(null);
        cus.setBussiness(null);

        System.out.println(cus.toString());
        cuservice.saveCustDetails(cus);

        return "redirect:/user_report";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model m, HttpSession session) {

        session.setAttribute("edit", "edit");
        System.out.println("in the edit mapping");
        return "redirect:/user_report";
    }

    @GetMapping("/analyzenow")
    public String Analyze() {
        System.out.println("inside analyze now");
        return "hotel";
    }

    @GetMapping("/feedback")
    public String getMethodName(@RequestParam("msg") String msg, AnalyzedUser au, Model m, HttpSession session) {
        String email = (String) session.getAttribute("Customeremail");
        Optional<AnalyzedUser> auser = auservice.findbyemails(email);
        au = auser.get();
        au.setFeedback(msg);
        auservice.savedetails(au);
        System.out.println(au);
        System.out.println(email);
        System.out.println(msg);

        return "redirect:/user_report";

    }

    @GetMapping("/userfeedback")
    public String userfeedback(Model m, AnalyzedUser au) {
        List<AnalyzedUser> li = auservice.getDetails();
        m.addAttribute("feedbacks", li);
        return "feedback";
    }
    @GetMapping("/userdata")
    public String UserData(Model m) {
        List<Customer> customers = cuservice.getCustomerDetails();
        // List<Customer> cuu = cuservice.getUserByStatus("unanalyzed");
        m.addAttribute("AllCustomers", customers);
        return "userdata";
    }
    @GetMapping("/deleteanalyst/{id}")
    public String deleteanalyst(@PathVariable int id) {
        service.deletebyid(id);
        return "redirect:/analyst";
    }
    
    

}

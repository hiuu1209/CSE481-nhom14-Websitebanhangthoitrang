package org.example.service;

import org.example.model.Customer;
import org.example.repository.CustomerRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class CustomerService {
    private final CustomerRepository customerRepository;
    private static final String CONFIRM_PASSWORD = "confirmPassword";
    private static final String NEW_PASSWORD = "newPassword";

    public CustomerService() {
        customerRepository = new CustomerRepository();
    }

    public Map<String, String> checkRegister(HttpServletRequest req) {
        Map<String, String> errors = new HashMap<>();
        String errorName = checkUserName(req.getParameter("customerName"));
        if (errorName != null) {
            errors.put("userName", errorName);
        }
        String errorNumberPhone = checkNumberPhone(req.getParameter("numberPhone"));
        if (errorNumberPhone != null) {
            errors.put("userNumberPhone", errorNumberPhone);
        }
        String errorAddress = checkAddress(req.getParameter("address"));
        if (errorAddress != null) {
            errors.put("userAddress", errorAddress);
        }
        String errorEmail = checkEmail(req.getParameter("email"));
        if (errorEmail != null) {
            errors.put("userEmail", errorEmail);
        }
        String errorPassword = checkPassword(req.getParameter("password"));
        if (errorPassword != null) {
            errors.put("userPassword", errorPassword);
        }
        String errorConfirmPassword = checkConfirmPassword(req.getParameter("password"), req.getParameter(CONFIRM_PASSWORD));
        if (errorConfirmPassword != null) {
            errors.put("userConfirmPassword", errorConfirmPassword);
        }
        return errors;
    }

    public Map<String, String> checkUpdateUser(HttpServletRequest req) {
        Map<String, String> errors = new HashMap<>();
        String errorName = checkUserName(req.getParameter("customerName"));
        if (errorName != null) {
            errors.put("userName", errorName);
        }
        String errorNumberPhone = checkNumberPhone(req.getParameter("numberPhone"));
        if (errorNumberPhone != null) {
            errors.put("userNumberPhone", errorNumberPhone);
        }
        String errorAddress = checkAddress(req.getParameter("address"));
        if (errorAddress != null) {
            errors.put("userAddress", errorAddress);
        }
        return errors;
    }

    public String checkUserName(String userName) {
        if (userName.trim().length() == 0) {
            return "Vui l??ng nh???p h??? t??n";
        } else if (userName.length() > 50) {
            return "H??? t??n d??i qu?? gi???i h???n(50 k?? t???)";
        } else {
            return null;
        }

    }

    public String checkAddress(String address) {
        if (address.trim().length() == 0) {
            return "Vui l??ng nh???p ?????a ch???";
        } else if (address.length() > 100) {
            return "?????a ch??? d??i qu?? gi???i h???n(100 k?? t???)";
        } else {
            return null;
        }
    }

    public String checkNumberPhone(String numberPhone) {
        String numberPhonePattern1 = "^[0]{1}[0-9]{9}$";
        String numberPhonePattern2 = "^[+]{1}[8]{1}[4]{1}[0-9]{9}$";
        if (numberPhone.trim().length() == 0) {
            return "Vui l??ng nh???p s??? ??i???n tho???i";
        } else if (!Pattern.matches(numberPhonePattern1, numberPhone) && !Pattern.matches(numberPhonePattern2, numberPhone)) {
            return "S??? ??i???n tho???i kh??ng h???p l???";
        } else {
            return null;
        }
    }

    public boolean checkLogged(HttpServletRequest req) {
        HttpSession session = req.getSession();
        Customer customer = (Customer) session.getAttribute("user");
        if (customer == null) {
            return false;
        } else {
            return customerRepository.getCustomerById(customer.getCustomerId()) != null;
        }
    }

    public String checkEmail(String email) {
        String emailPattern = "^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$";
        if (email.trim().length() == 0) {
            return "Vui l??ng nh???p email";
        } else if (!Pattern.matches(emailPattern, email)) {
            return "Email kh??ng h???p l???";
        } else if (customerRepository.getCustomerByEmail(email) != null) {
            return "Email ???? ???????c s??? d???ng";
        } else {
            return null;
        }
    }

    public String checkPassword(String password) {
        String passwordPattern = "^[a-z0-9_-]{6,20}$";
        if (password.trim().length() == 0) {
            return "Vui l??ng nh???p m???t kh???u";
        } else if (!Pattern.matches(passwordPattern, password)) {
            return "M???t kh???u ph???i d??i t??? 6 ?????n 20 k?? t???";
        } else {
            return null;
        }
    }

    public String checkConfirmPassword(String password, String confirmPassword) {
        if (confirmPassword.trim().length() == 0) {
            return "Vui l??ng nh???p m???t kh???u x??c th???c";
        } else if (confirmPassword.trim().compareTo(password.trim()) != 0) {
            return "M???t kh???u kh??ng kh???p";
        } else {
            return null;
        }
    }

    public String checkStatusAccount(HttpServletRequest req) {
        if (req.getParameter("statusActive").compareTo("1") != 0 && req.getParameter("statusActive").compareTo("0") != 0) {
            return "Tr???ng th??i t??i kho???n kh??ng h???p l???";
        } else {
            return null;
        }
    }

    public String md5(String password) {
        String passwordEncoded = null;
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.update(password.getBytes());
            BigInteger bigInteger = new BigInteger(1, digest.digest());
            passwordEncoded = bigInteger.toString(16);
        } catch (NoSuchAlgorithmException e) {
            passwordEncoded = null;
        }
        return passwordEncoded;
    }

    public int insertUser(Customer customer) {
        return customerRepository.insertUser(customer);
    }

    public Customer checkLogin(Customer customer) {
        return customerRepository.login(customer);
    }

    public Customer getCustomerById(String customerId) {
        try {
            return customerRepository.getCustomerById(Integer.parseInt(customerId));
        } catch (Exception ex) {
            return null;
        }
    }

    public int updateCustomer(Customer customer) {
        return customerRepository.updateCustomer(customer);
    }

    public Map<String, String> checkChangePassword(HttpServletRequest req) {
        Map<String, String> errors = new HashMap<>();
        HttpSession session = req.getSession();
        Customer customerLogin = (Customer) session.getAttribute("user");
        Customer customer = customerRepository.getCustomerById(customerLogin.getCustomerId());
        if (customer.getPassword().compareTo(md5(req.getParameter("currentPassword"))) != 0) {
            errors.put("currentPassword", "M???t kh???u hi???n t???i kh??ng ch??nh x??c");
        }
        String errorPassword = checkPassword(req.getParameter(NEW_PASSWORD));
        if (errorPassword != null) {
            errors.put(NEW_PASSWORD, errorPassword);
        }
        String errorConfirmPassword = checkConfirmPassword(req.getParameter(NEW_PASSWORD), req.getParameter(CONFIRM_PASSWORD));
        if (errorConfirmPassword != null) {
            errors.put(CONFIRM_PASSWORD, errorConfirmPassword);
        }
        return errors;
    }

    public int changePassword(Customer customer) {
        return customerRepository.changePassword(customer);
    }

    public List<Customer> getCustomers() {
        return customerRepository.getCustomer();
    }

    public int changeStatusAccount(Customer customer) {
        return customerRepository.changeStatusAccount(customer);
    }

    public List<Customer> searchCustomer(String customerName) {
        if (customerName == null || customerName.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return customerRepository.searchCustomer(customerName);
    }
}

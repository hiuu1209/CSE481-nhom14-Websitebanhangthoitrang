package org.example.service;

import org.apache.commons.io.FilenameUtils;
import org.example.model.Admin;
import org.example.repository.AdminRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class AdminService {
    private final AdminRepository adminRepository;
    private static final String CONFIRM_PASSWORD = "confirmPassword";
    private static final String NEW_PASSWORD = "newPassword";

    public AdminService() {
        adminRepository = new AdminRepository();
    }

    public Map<String, String> checkInsertAdmin(HttpServletRequest req) {
        Map<String, String> errors = new HashMap<>();
        String errorName = checkAdminName(req.getParameter("name"));
        if (errorName != null) {
            errors.put("adminName", errorName);
        }
        String errorNumberPhone = checkNumberPhone(req.getParameter("numberPhone"));
        if (errorNumberPhone != null) {
            errors.put("adminNumberPhone", errorNumberPhone);
        }
        String errorSex = checkSexAdmin(req.getParameter("sex"));
        if (errorSex != null) {
            errors.put("adminSex", errorSex);
        }
        String errorAddress = checkAddress(req.getParameter("address"));
        if (errorAddress != null) {
            errors.put("adminAddress", errorAddress);
        }
        String errorEmail = checkEmail(req.getParameter("email"));
        if (errorEmail != null) {
            errors.put("adminEmail", errorEmail);
        }
        String errorPassword = checkPassword(req.getParameter("password"));
        if (errorPassword != null) {
            errors.put("adminPassword", errorPassword);
        }
        String errorConfirmPassword = checkConfirmPassword(req.getParameter("password"), req.getParameter(CONFIRM_PASSWORD));
        if (errorConfirmPassword != null) {
            errors.put("adminConfirmPassword", errorConfirmPassword);
        }
        return errors;
    }

    public Map<String, String> checkUpdateAdmin(HttpServletRequest req) {
        Map<String, String> errors = new HashMap<>();
        String errorName = checkAdminName(req.getParameter("name"));
        if (errorName != null) {
            errors.put("adminName", errorName);
        }
        String errorNumberPhone = checkNumberPhone(req.getParameter("numberPhone"));
        if (errorNumberPhone != null) {
            errors.put("adminNumberPhone", errorNumberPhone);
        }
        String errorSex = checkSexAdmin(req.getParameter("sex"));
        if (errorSex != null) {
            errors.put("adminSex", errorSex);
        }
        String errorAddress = checkAddress(req.getParameter("address"));
        if (errorAddress != null) {
            errors.put("adminAddress", errorAddress);
        }
        return errors;
    }

    public String checkAdminName(String adminName) {
        if (adminName.trim().length() == 0) {
            return "Vui l??ng nh???p h??? t??n c???a admin";
        } else if (adminName.length() > 50) {
            return "H??? t??n c???a admin d??i qu?? gi???i h???n(50 k?? t???)";
        } else {
            return null;
        }

    }

    public String checkSexAdmin(String sex) {
        if (sex.trim().length() == 0) {
            return "Vui l??ng ch???n gi???i t??nh c???a admin";
        } else if (sex.length() > 6) {
            return "Gi???i t??nh c???a admin d??i q??a gi???i h???n(6 k?? t???)";
        } else {
            return null;
        }
    }

    public String checkAddress(String address) {
        if (address.trim().length() == 0) {
            return "Vui l??ng nh???p ?????a ch??? c???a admin";
        } else if (address.length() > 100) {
            return "?????a ch??? c???a admin d??i qu?? gi???i h???n(100 k?? t???)";
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

    public String checkEmail(String email) {
        String emailPattern = "^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$";
        if (email.trim().length() == 0) {
            return "Vui l??ng nh???p email";
        } else if (!Pattern.matches(emailPattern, email)) {
            return "Email kh??ng h???p l???";
        } else if (adminRepository.getAdminByEmail(email) != null) {
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

    public String getNameImage(Part filePart, String upLoads) {
        String fileName = null;
        String fileNameNew = null;
        if (filePart.getSubmittedFileName() != null && filePart.getSubmittedFileName().length() != 0) {
            fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            try {
                fileNameNew = FilenameUtils.getBaseName(fileName) + "-" + System.nanoTime() + "." + FilenameUtils.getExtension(fileName);
                InputStream fileContent = filePart.getInputStream();
                File file = new File(upLoads, fileNameNew);
                Files.copy(fileContent, file.toPath());
            } catch (IOException e) {
                fileNameNew = null;
            }
        }
        return fileNameNew;
    }

    public boolean checkLogged(HttpServletRequest req) {
        HttpSession session = req.getSession();
        Admin admin = (Admin) session.getAttribute("adminLogin");
        if (admin == null) {
            return false;
        } else {
            return adminRepository.getAdminById(admin.getAdminId()) != null;
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

    public int insertAdmin(Admin admin) {
        return adminRepository.insertAdmin(admin);
    }

    public List<Admin> getAdmin() {
        return adminRepository.getAdmin();
    }

    public Admin getAdminById(String adminId) {
        try {
            return adminRepository.getAdminById(Integer.parseInt(adminId));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public int updateAdmin(Admin admin) {
        return adminRepository.updateAdmin(admin);
    }

    public Admin login(Admin admin) {
        return adminRepository.login(admin);
    }

    public Map<String, String> checkChangePassword(HttpServletRequest req) {
        HttpSession session = req.getSession();
        Admin adminLogin = (Admin) session.getAttribute("adminLogin");
        Admin admin = adminRepository.getAdminById(adminLogin.getAdminId());
        Map<String, String> errors = new HashMap<>();
        if (admin.getPassword().compareTo(md5(req.getParameter("currentPassword"))) != 0) {
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

    public int changePassword(Admin admin) {
        return adminRepository.changePassword(admin);
    }

    public List<Admin> searchAdmin(String adminName) {
        if (adminName == null || adminName.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return adminRepository.searchAdmin(adminName);
    }

}

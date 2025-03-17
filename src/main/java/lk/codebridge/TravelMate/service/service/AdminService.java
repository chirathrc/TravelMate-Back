package lk.codebridge.TravelMate.service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lk.codebridge.TravelMate.entity.Admin;
import lk.codebridge.TravelMate.entity.Position;

@Service
public interface AdminService {
    
    public Admin addAdmin(Admin admin);
    
    public boolean removeAdmin(Admin admin);

    public Admin upadateAdmin(Admin admin);

    public Admin getAdmin(String email);

    public Admin getAdmin(String email, String password);
    
    public Admin getAdmin(String email, String password, String mobile);

    public Admin getAdminByMobile(String mobile);

    public List<Admin> getAllAdmins();

    public List<Position> getAllPositions();

    public Position getPosition(String name);


}

package lk.codebridge.TravelMate.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lk.codebridge.TravelMate.entity.Admin;
import lk.codebridge.TravelMate.entity.Position;
import lk.codebridge.TravelMate.repository.AdminRepository;
import lk.codebridge.TravelMate.repository.PositionRepository;
import lk.codebridge.TravelMate.service.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {
    
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Override
    public Admin addAdmin(Admin admin) {
      
        Admin newAdmin = adminRepository.save(admin);

        if (newAdmin != null) {
            return newAdmin;
        } else {
            return null;
        }
    }

    @Override
    public boolean removeAdmin(Admin admin) {
        throw new UnsupportedOperationException("Unimplemented method 'removeAdmin'");
    }

    @Override
    public Admin upadateAdmin(Admin admin) {
       
        Admin updateAdmin = adminRepository.save(admin);

        if (updateAdmin != null) {
            return updateAdmin;
        } else {
            return null;
        }
        
    }

    @Override
    public Admin getAdmin(String email) {
        
        Admin admin = adminRepository.findByEmail(email);

        if (admin != null) {
            return admin;
        } else {
            return null;
        }
    }

    @Override
    public Admin getAdmin(String email, String password) {
      
        Admin admin = adminRepository.findByEmailAndPassword(email, password);

        if (admin != null) {
            return admin;
        } else {
            return null;
        }

    }

    @Override
    public Admin getAdmin(String email, String password, String mobile) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAdmin'");
    }

    @Override
    public List<Admin> getAllAdmins() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllAdmins'");
    }

    @Override
    public List<Position> getAllPositions() {
      
        return positionRepository.findAll();
    }

    @Override
    public Position getPosition(String name) {
       
        return positionRepository.findByName(name);
    }

    @Override
    public Admin getAdminByMobile(String mobile) {
        
        return adminRepository.findByMobile(mobile);

    }
    
}

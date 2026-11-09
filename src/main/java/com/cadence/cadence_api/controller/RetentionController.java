package com.cadence.cadence_api.controller;

import com.cadence.cadence_api.dto.MemberRetention;
import com.cadence.cadence_api.model.Member;
import com.cadence.cadence_api.service.RetentionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/retention")
@CrossOrigin(origins = "http://localhost:5173")
public class RetentionController {

    private final RetentionService retentionService;

    public RetentionController(RetentionService retentionService) {
        this.retentionService = retentionService;
    }

    @GetMapping("/overall")
    public Map<String, Object> getOverallRetention() {
        return retentionService.getOverallRetention();
    }

    @GetMapping("/classes")
    public List<Map<String, Object>> getClassRetention() {
        return retentionService.getClassRetention();
    }

    @GetMapping("/inactive")
    public List<MemberRetention> getInactiveMembers() {
        return retentionService.getInactiveMembers();
    }

    @GetMapping("/never-booked")
    public List<MemberRetention> getNeverBookedMembers() {
        return retentionService.getNeverBookedMembers();
    }

    @GetMapping("/cancelled-only")
    public List<MemberRetention> getCancelledOnlyMembers() {
        return retentionService.getCancelledOnlyMembers();
    }
}

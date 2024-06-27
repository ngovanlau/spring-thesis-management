package com.thesisSpringApp.api;

import com.thesisSpringApp.Dto.CriteriaDetailDTO;
import com.thesisSpringApp.Dto.NewCriteriaDTO;
import com.thesisSpringApp.pojo.Criteria;
import com.thesisSpringApp.service.CriteriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/criteria")
public class CriteriaApiController {
    private CriteriaService criteriaService;

    @Autowired
    public CriteriaApiController(CriteriaService criteriaService) {
        this.criteriaService = criteriaService;
    }

    public List<CriteriaDetailDTO> responseCriteriaDetails() {
        List<Criteria> criteriaList = criteriaService.getCriteriaList();

        List<CriteriaDetailDTO> criteriaDetailDTOList = new ArrayList<>();
        criteriaList.forEach(criteria -> {
            CriteriaDetailDTO criteriaDetailDTO = new CriteriaDetailDTO();
            criteriaDetailDTO.setId(criteria.getId());
            criteriaDetailDTO.setName(criteria.getName());

            criteriaDetailDTOList.add(criteriaDetailDTO);
        });

        return criteriaDetailDTOList;
    }

    @GetMapping("/")
    @CrossOrigin
    public ResponseEntity<List<CriteriaDetailDTO>> list() {
        List<CriteriaDetailDTO> criteriaDetailDTOList = responseCriteriaDetails();

        return new ResponseEntity<>(criteriaDetailDTOList, HttpStatus.OK);
    }

    @PostMapping("/")
    @CrossOrigin
    public ResponseEntity<List<CriteriaDetailDTO>> create(@RequestBody NewCriteriaDTO newCriteriaDTO) {

        if (newCriteriaDTO.getName().isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Criteria criteria = new Criteria(newCriteriaDTO.getName());
        criteriaService.saveAndUpdateCriteria(criteria);

        List<CriteriaDetailDTO> criteriaDetailDTOList = responseCriteriaDetails();

        return new ResponseEntity<>(criteriaDetailDTOList, HttpStatus.CREATED);
    }
}

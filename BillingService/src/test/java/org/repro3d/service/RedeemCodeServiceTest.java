package org.repro3d.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.repro3d.model.RedeemCode;
import org.repro3d.repository.RedeemCodeRepository;
import org.repro3d.utils.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RedeemCodeServiceTest {

    @Mock
    private RedeemCodeRepository redeemCodeRepository;

    @InjectMocks
    private RedeemCodeService redeemCodeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createRedeemCode_SuccessfulCreation_ReturnsOk() {
        RedeemCode redeemCode = new RedeemCode(null, "someCode", false);
        when(redeemCodeRepository.save(redeemCode)).thenReturn(redeemCode);

        ResponseEntity<ApiResponse> response = redeemCodeService.createRedeemCode(redeemCode);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isSuccess());
        assertEquals(redeemCode, response.getBody().getData());
    }

    @Test
    public void generateRedeemCode_SuccessfulGeneration_ReturnsOk() {
        String randomCode = UUID.randomUUID().toString();
        String encodedCode = Base64.getEncoder().encodeToString(randomCode.getBytes());
        RedeemCode redeemCode = new RedeemCode(null, encodedCode, false);
        when(redeemCodeRepository.save(any(RedeemCode.class))).thenReturn(redeemCode);

        ResponseEntity<ApiResponse> response = redeemCodeService.generateRedeemCode();

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isSuccess());
        assertNotNull(response.getBody().getData());
    }

    @Test
    public void validateRedeemCode_ValidCode_ReturnsValid() {
        String encodedCode = Base64.getEncoder().encodeToString("validCode".getBytes());
        RedeemCode redeemCode = new RedeemCode(1L, encodedCode, false);
        when(redeemCodeRepository.findByRcCode(encodedCode)).thenReturn(Optional.of(redeemCode));

        ResponseEntity<ApiResponse> response = redeemCodeService.validateRedeemCode(encodedCode);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isSuccess());
    }

    @Test
    public void validateRedeemCode_InvalidCode_ReturnsInvalid() {
        String encodedCode = Base64.getEncoder().encodeToString("invalidCode".getBytes());
        when(redeemCodeRepository.findByRcCode(encodedCode)).thenReturn(Optional.empty());

        ResponseEntity<ApiResponse> response = redeemCodeService.validateRedeemCode(encodedCode);

        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isSuccess());
    }

    @Test
    public void getRedeemCodeById_CodeExists_ReturnsCode() {
        Long redeemCodeId = 1L;
        RedeemCode redeemCode = new RedeemCode(redeemCodeId, "someCode", false);
        when(redeemCodeRepository.findById(redeemCodeId)).thenReturn(Optional.of(redeemCode));

        ResponseEntity<ApiResponse> response = redeemCodeService.getRedeemCodeById(redeemCodeId);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isSuccess());
        assertEquals(redeemCode, response.getBody().getData());
    }

    @Test
    public void getRedeemCodeById_CodeDoesNotExist_ReturnsNotFound() {
        Long redeemCodeId = 1L;
        when(redeemCodeRepository.findById(redeemCodeId)).thenReturn(Optional.empty());

        ResponseEntity<ApiResponse> response = redeemCodeService.getRedeemCodeById(redeemCodeId);

        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isSuccess());
    }

    @Test
    public void getAllRedeemCodes_CodesExist_ReturnsAllCodes() {
        List<RedeemCode> redeemCodes = List.of(
                new RedeemCode(1L, "code1", false),
                new RedeemCode(2L, "code2", true)
        );
        when(redeemCodeRepository.findAll()).thenReturn(redeemCodes);

        ResponseEntity<ApiResponse> response = redeemCodeService.getAllRedeemCodes();

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isSuccess());
        assertEquals(redeemCodes, response.getBody().getData());
    }

    @Test
    public void updateRedeemCode_CodeExists_UpdatesCode() {
        Long redeemCodeId = 1L;
        RedeemCode existingCode = new RedeemCode(redeemCodeId, "oldCode", false);
        RedeemCode newCodeDetails = new RedeemCode(redeemCodeId, "newCode", true);
        when(redeemCodeRepository.findById(redeemCodeId)).thenReturn(Optional.of(existingCode));
        when(redeemCodeRepository.save(existingCode)).thenReturn(newCodeDetails);

        ResponseEntity<ApiResponse> response = redeemCodeService.updateRedeemCode(redeemCodeId, newCodeDetails);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isSuccess());
        assertEquals(newCodeDetails, response.getBody().getData());
    }

    @Test
    public void updateRedeemCode_CodeDoesNotExist_ReturnsNotFound() {

        Long redeemCodeId = 1L;
        RedeemCode redeemCodeDetails = new RedeemCode(redeemCodeId, "newCode", true);
        when(redeemCodeRepository.findById(redeemCodeId)).thenReturn(Optional.empty());

        ResponseEntity<ApiResponse> response = redeemCodeService.updateRedeemCode(redeemCodeId, redeemCodeDetails);

        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isSuccess());
    }

    @Test
    public void deleteRedeemCode_CodeExists_DeletesCode() {
        Long redeemCodeId = 1L;
        when(redeemCodeRepository.existsById(redeemCodeId)).thenReturn(true);
        doNothing().when(redeemCodeRepository).deleteById(redeemCodeId);

        ResponseEntity<ApiResponse> response = redeemCodeService.deleteRedeemCode(redeemCodeId);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isSuccess());
    }

    @Test
    public void deleteRedeemCode_CodeDoesNotExist_ReturnsNotFound() {
        Long redeemCodeId = 1L;
        when(redeemCodeRepository.existsById(redeemCodeId)).thenReturn(false);

        ResponseEntity<ApiResponse> response = redeemCodeService.deleteRedeemCode(redeemCodeId);

        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isSuccess());
    }
}

package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.repository.ParkingBoyRepository;
import com.thoughtworks.springbootemployee.service.ParkingBoyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ParkingBoyControllerTest {

    ParkingBoyRepository parkingBoyRepository = Mockito.mock(ParkingBoyRepository.class);

    ParkingBoyService parkingBoyService = new ParkingBoyService(parkingBoyRepository);

    @Test
    public void should_get_all_parking_boy_successfully() {
        parkingBoyService.getAll();

        Mockito.verify(parkingBoyRepository, Mockito.times(1)).findAll();
    }
}

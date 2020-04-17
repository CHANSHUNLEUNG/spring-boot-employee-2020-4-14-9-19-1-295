package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.model.ParkingBoy;
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

    @Test
    public void should_create_parking_boy_when_given_a_new_parking_boy() {
        ParkingBoy parkingBoy = new ParkingBoy(1,"leo",
                new Employee(1,"leo chan",18,"male",80000,1));

        parkingBoyService.addParkingBoy(parkingBoy);

        Mockito.verify(parkingBoyRepository,Mockito.times(1)).save(parkingBoy);
    }
}

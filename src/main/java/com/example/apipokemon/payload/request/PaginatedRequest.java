package com.example.apipokemon.payload.request;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class PaginatedRequest {
    @NotNull
    private int numeroPage;
    @NotNull
    private int nombreElements;
}

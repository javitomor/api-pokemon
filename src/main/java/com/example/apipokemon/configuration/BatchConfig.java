package com.example.apipokemon.configuration;

import javax.sql.DataSource;

import com.example.apipokemon.enums.SortFieldsPokemonEnum;
import com.example.apipokemon.model.Pokemon;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;


@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;


    @Value("classPath:/data/pokemon.csv")
    private Resource inputResource;

    @Bean
    public Job readCSVFileJob() {
        return jobBuilderFactory
                .get("readCSVFileJob")
                .incrementer(new RunIdIncrementer())
                .start(step())
                .build();
    }

    @Bean
    public Step step() {
        return stepBuilderFactory
                .get("step")
                .<Pokemon, Pokemon>chunk(5)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public ItemProcessor<Pokemon, Pokemon> processor() {
        return new DBLogProcessor();
    }

    @Bean
    public FlatFileItemReader<Pokemon> reader() {
        FlatFileItemReader<Pokemon> itemReader = new FlatFileItemReader<Pokemon>();
        itemReader.setLineMapper(lineMapper());
        itemReader.setLinesToSkip(1);
        itemReader.setResource(inputResource);
        return itemReader;
    }

    @Bean
    public LineMapper<Pokemon> lineMapper() {
        DefaultLineMapper<Pokemon> lineMapper = new DefaultLineMapper<Pokemon>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        BeanWrapperFieldSetMapper<Pokemon> fieldSetMapper = new BeanWrapperFieldSetMapper<Pokemon>();

        lineTokenizer.setNames(getNamesLineTokenizer());
        lineTokenizer.setIncludedFields(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12});

        fieldSetMapper.setTargetType(Pokemon.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }


    private String[] getNamesLineTokenizer() {
        return new String[]{
                SortFieldsPokemonEnum.ID.getColumn(),
                SortFieldsPokemonEnum.NAME.getColumn(),
                SortFieldsPokemonEnum.TYPE1.getColumn(),
                SortFieldsPokemonEnum.TYPE2.getColumn(),
                SortFieldsPokemonEnum.TOTAL.getColumn(),
                SortFieldsPokemonEnum.HP.getColumn(),
                SortFieldsPokemonEnum.ATTACK.getColumn(),
                SortFieldsPokemonEnum.DEFENSE.getColumn(),
                SortFieldsPokemonEnum.SP_ATK.getColumn(),
                SortFieldsPokemonEnum.SP_DEF.getColumn(),
                SortFieldsPokemonEnum.SPEED.getColumn(),
                SortFieldsPokemonEnum.GENERATION.getColumn(),
                SortFieldsPokemonEnum.LEGENDARY.getColumn()
        };
    }

    @Bean
    public JdbcBatchItemWriter<Pokemon> writer() {
        JdbcBatchItemWriter<Pokemon> itemWriter = new JdbcBatchItemWriter<Pokemon>();
        itemWriter.setDataSource(dataSourcePokemon());
        itemWriter.setSql(getSqlInsertPokemon());
        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Pokemon>());
        itemWriter.afterPropertiesSet();
        return itemWriter;
    }


    private String getSqlInsertPokemon() {
        String sql = "insert into pokemon select * from (" +
                "select "+
                ":"+SortFieldsPokemonEnum.ID.getAttribute() +" as "+SortFieldsPokemonEnum.ID.getColumn() +","+
                ":"+SortFieldsPokemonEnum.NAME.getAttribute() +" as "+SortFieldsPokemonEnum.NAME.getColumn() +","+
                ":"+SortFieldsPokemonEnum.TYPE1.getAttribute()+" as "+SortFieldsPokemonEnum.TYPE1.getColumn() +","+
                ":"+SortFieldsPokemonEnum.TYPE2.getAttribute() +" as "+SortFieldsPokemonEnum.TYPE2.getColumn() +","+
                ":"+SortFieldsPokemonEnum.TOTAL.getAttribute() +" as "+SortFieldsPokemonEnum.TOTAL.getColumn() +","+
                ":"+SortFieldsPokemonEnum.HP.getAttribute() +" as "+SortFieldsPokemonEnum.HP.getColumn() +","+
                ":"+SortFieldsPokemonEnum.ATTACK.getAttribute() +" as "+SortFieldsPokemonEnum.ATTACK.getColumn() +","+
                ":"+SortFieldsPokemonEnum.DEFENSE.getAttribute() +" as "+SortFieldsPokemonEnum.DEFENSE.getColumn() +","+
                ":"+SortFieldsPokemonEnum.SP_ATK.getAttribute() +" as "+SortFieldsPokemonEnum.SP_ATK.getColumn() +","+
                ":"+SortFieldsPokemonEnum.SP_DEF.getAttribute() +" as "+SortFieldsPokemonEnum.SP_DEF.getColumn() +","+
                ":"+SortFieldsPokemonEnum.SPEED.getAttribute() +" as "+SortFieldsPokemonEnum.SPEED.getColumn() +","+
                ":"+SortFieldsPokemonEnum.GENERATION.getAttribute() +" as "+SortFieldsPokemonEnum.GENERATION.getColumn() +","+
                ":"+SortFieldsPokemonEnum.LEGENDARY.getAttribute() +" as "+SortFieldsPokemonEnum.LEGENDARY.getColumn()+
                ") X where not exists(SELECT * FROM pokemon WHERE "+  SortFieldsPokemonEnum.ID.getColumn()+"= :"+SortFieldsPokemonEnum.ID.getAttribute()+")";
        return sql;
    }

    public DataSource dataSourcePokemon() {

        EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder();

        return embeddedDatabaseBuilder.addScript("classpath:org/springframework/batch/core/schema-drop-h2.sql")
                .addScript("classpath:org/springframework/batch/core/schema-h2.sql")
                .setType(EmbeddedDatabaseType.H2)
                .build();
    }


}

# :construction: - Desafio: DsMovie-Jacoco

- Projeto de filmes e avaliações de filmes;

##

## :white_check_mark: - Competências Avaliadas:

- Testes unitários em projeto Spring Boot com Java;
- Implementação de testes unitários com JUnit e Mockito;
- Cobertura de código com Jacoco;

##

## :computer: - Tecnologias: 

- Java;
- Spring Boot;
- JUnit;
- Mockito;
- H2;
- JPA;
- Oauth2;
- Spring Security;
- Maven;
- Jacoco;

##

## :gear: - Testes unitários:

#### :open_file_folder: - MovieServiceTests:

- ##### findAllShouldReturnPagedMovieDTO;
- ##### findByIdShouldReturnMovieDTOWhenIdExists;
- ##### findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist;
- ##### insertShouldReturnMovieDTO;
- ##### updateShouldReturnMovieDTOWhenIdExists;
- ##### updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist;
- ##### deleteShouldDoNothingWhenIdExists;
- ##### deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist;
- ##### deleteShouldThrowDatabaseExceptionWhenDependentId;

##

#### :open_file_folder: - ScoreServiceTests:

- ##### saveScoreShouldReturnMovieDTO;
- ##### saveScoreShouldThrowResourceNotFoundExceptionWhenNonExistingMovieId;

##

#### :open_file_folder: - UserServiceTests:

- ##### authenticatedShouldReturnUserEntityWhenUserExists
- ##### authenticatedShouldThrowUsernameNotFoundExceptionWhenUserDoesNotExists
- ##### loadUserByUsernameShouldReturnUserDetailsWhenUserExists
- ##### loadUserByUsernameShouldThrowUsernameNotFoundExceptionWhenUserDoesNotExists

##

## :pushpin: - Objetivos:

- [x] - MovieService.findAll() deve retornar uma página de filmes;
- [x] - MovieService.findById(id) deve retornar um filme quando o id existir;
- [x] - MovieService.findById(id) deve lançar ResourceNotFoundException quando o id não existir;
- [x] - MovieService.insert(dto) deve retornar um filme;
- [x] - MovieService.update(id, dto) deve retornar um filme quando o id existir;
- [x] - MovieService.update(id, dto) deve lançar ResourceNotFoundException quando o id não existir;
- [x] - MovieService.delete(id) deve fazer nada quando o id existir;
- [x] - MovieService.delete(id) deve lançar ResourceNotFoundException quando o id não existir;
- [x] - MovieService.delete(id) deve lançar DatabaseException quando o id for dependente;
- [x] - UserService.authenticated() deve retornar um usuário quando houver usuário logado;
- [x] - UserService.authenticated() deve lançar UsernameNotFoundException quando não houver usuário logado;
- [x] - UserService.loadUserByUsername(username) deve retornar um UserDetails quando o username existir;
- [x] - UserService.loadUserByUsername(username) deve lançar UsernameNotFoundException quando o username não existir;
- [x] - ScoreService.saveScore(dto) deve retornar os dados do filme quando o id existir;
- [x] - ScoreService.saveScore(dto) deve lançar ResourceNotFoundException quando o id do filme não existir;

##

## :clipboard: - Diagrama:

![dsmovie-jacoco drawio](https://github.com/carloshenriquefs/dsmovie-jacoco/assets/54969405/f37c94bc-b127-405a-9a74-cf81b1d8ec2d)

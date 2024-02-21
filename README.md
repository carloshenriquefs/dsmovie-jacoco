# :construction: - Desafio: DsMovie-Jacoco

- Projeto de filmes e avaliações de filmes;

##

## :computer: - Tecnologias: 

- Java
- Spring Boot;
- JUnit;
- Mockito;
- H2
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

- [] - MovieService.findAll() deve retornar uma página de filmes;
- [] - MovieService.findById(id) deve retornar um filme quando o id existir;
- [] - MovieService.findById(id) deve lançar ResourceNotFoundException quando o id não existir;
- [] - MovieService.insert(dto) deve retornar um filme;
- [] - MovieService.update(id, dto) deve retornar um filme quando o id existir;
- [] - MovieService.update(id, dto) deve lançar ResourceNotFoundException quando o id não existir;
- [] - MovieService.delete(id) deve fazer nada quando o id existir;
- [] - MovieService.delete(id) deve lançar ResourceNotFoundException quando o id não existir;
- [] - MovieService.delete(id) deve lançar DatabaseException quando o id for dependente;
- [] - UserService.authenticated() deve retornar um usuário quando houver usuário logado;
- [] - UserService.authenticated() deve lançar UsernameNotFoundException quando não houver usuário logado;
- [] - UserService.loadUserByUsername(username) deve retornar um UserDetails quando o username existir;
- [] - UserService.loadUserByUsername(username) deve lançar UsernameNotFoundException quando o username não existir;
- [] - ScoreService.saveScore(dto) deve retornar os dados do filme quando o id existir;
- [] - ScoreService.saveScore(dto) deve lançar ResourceNotFoundException quando o id do filme não existir;

##

## :clipboard: - Diagrama:

![dsmovie-jacoco drawio](https://github.com/carloshenriquefs/dsmovie-jacoco/assets/54969405/f37c94bc-b127-405a-9a74-cf81b1d8ec2d)

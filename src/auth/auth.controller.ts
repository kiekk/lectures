import {
  Body,
  Controller,
  Post,
  UseGuards,
  ValidationPipe,
} from '@nestjs/common'
import { AuthService } from './auth.service'
import { AuthCredentialDto } from './dto/auth-credential.dto'
import { AuthGuard } from '@nestjs/passport'
import { GetUser } from './get-user.decorator'
import { User } from './user.entity'

@Controller('auth')
export class AuthController {
  constructor(private authService: AuthService) {}

  @Post('/signup')
  signUp(@Body() authCredentialDto: AuthCredentialDto): Promise<void> {
    return this.authService.signUp(authCredentialDto)
  }

  @Post('/signin')
  signIn(
    @Body(ValidationPipe) authCredentialDto: AuthCredentialDto,
  ): Promise<{ accessToken: string }> {
    return this.authService.signIn(authCredentialDto)
  }

  // jwt token test
  @Post('/test')
  // request안에 user 객체가 들어가게 해줌
  @UseGuards(AuthGuard())
  test(@GetUser() user: User) {
    console.log('user', user)
  }
}

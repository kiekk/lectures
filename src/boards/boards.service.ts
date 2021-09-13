import { Injectable, NotFoundException } from '@nestjs/common';
import { BoardStatus } from './enum/boardStatus';
import { CreateBoardDto } from './dto/create-board.dto';
import { InjectRepository } from '@nestjs/typeorm';
import { BoardRepository } from './board.repository';
import { Board } from './board.entity';
import { User } from '../auth/user.entity';

@Injectable()
export class BoardsService {
  constructor(
    @InjectRepository(BoardRepository) private boardRepository: BoardRepository,
  ) {}

  async getAllBoards(user: User): Promise<Board[]> {
    return this.boardRepository.getAllBoards(user);
  }

  async createBoard(
    createBoardDto: CreateBoardDto,
    user: User,
  ): Promise<Board> {
    return this.boardRepository.createBoard(createBoardDto, user);
  }

  async getBoardById(id: number): Promise<Board> {
    return this.boardRepository.getBoardById(id);
  }

  async deleteBoard(id: number): Promise<void> {
    await this.boardRepository.deleteBoard(id);
  }

  async updateBoardStatus(id: number, status: BoardStatus): Promise<Board> {
    const board = await this.getBoardById(id);
    board.status = status;
    await this.boardRepository.updateBoardStatus(board);
    return board;
  }
}

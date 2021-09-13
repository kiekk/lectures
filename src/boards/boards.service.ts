import { Injectable, NotFoundException } from '@nestjs/common';
import { BoardStatus } from './enum/boardStatus';
import { CreateBoardDto } from './dto/create-board.dto';
import { InjectRepository } from '@nestjs/typeorm';
import { BoardRepository } from './board.repository';
import { Board } from './board.entity';

@Injectable()
export class BoardsService {
  constructor(
    @InjectRepository(BoardRepository) private boardRepository: BoardRepository,
  ) {}
  // // 타입 추론이 되기 때문에 타입을 지정하지 않아도 Board[]로 인식
  // getAllBoards() /*: Board[] */ {
  //   return this.boards;
  // }

  async createBoard(createBoardDto: CreateBoardDto): Promise<Board> {
    return this.boardRepository.createBoard(createBoardDto);
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

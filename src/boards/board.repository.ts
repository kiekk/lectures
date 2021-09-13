import { EntityRepository, Repository } from 'typeorm';
import { Board } from './board.entity';
import { CreateBoardDto } from './dto/create-board.dto';
import { BoardStatus } from './enum/boardStatus';
import { NotFoundException } from '@nestjs/common';
import { User } from '../auth/user.entity';

@EntityRepository(Board)
export class BoardRepository extends Repository<Board> {
  async getAllBoards(user: User): Promise<Board[]> {
    const query = this.createQueryBuilder('board');

    query.where('board.userId = :userId', { userId: user.id });

    const boards = await query.getMany();
    return boards;
  }

  async createBoard(
    createBoardDto: CreateBoardDto,
    user: User,
  ): Promise<Board> {
    const { title, description } = createBoardDto;

    const board = this.create({
      title,
      description,
      status: BoardStatus.PUBLIC,
      user,
    });

    await this.save(board);
    return board;
  }

  async getBoardById(id: number): Promise<Board> {
    const found = await this.findOne(id);

    if (!found) {
      throw new NotFoundException(`Can't find Board with id {${id}}`);
    }

    return found;
  }

  async deleteBoard(id: number): Promise<void> {
    const result = await this.delete(id);

    if (result.affected === 0) {
      throw new NotFoundException(`Can't find Board with id {${id}}`);
    }
  }

  async updateBoardStatus(board: Board): Promise<Board> {
    return this.save(board);
  }
}

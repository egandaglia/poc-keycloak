import { Author } from './author';

export interface Article {
  title: string;
  slug: string;
  imageUrl: string;
  categoryId: number;
  body: string;
  createdAt?: Date;
  updatedAt?: Date;
  author: Author;
}

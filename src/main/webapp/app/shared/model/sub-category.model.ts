import { ICategory } from '@/shared/model/category.model';

export interface ISubCategory {
  id?: number;
  nameUzLat?: string | null;
  nameUzKr?: string | null;
  nameRu?: string | null;
  nameEn?: string | null;
  category?: ICategory | null;
}

export class SubCategory implements ISubCategory {
  constructor(
    public id?: number,
    public nameUzLat?: string | null,
    public nameUzKr?: string | null,
    public nameRu?: string | null,
    public nameEn?: string | null,
    public category?: ICategory | null
  ) {}
}

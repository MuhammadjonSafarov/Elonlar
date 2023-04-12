export interface ICategory {
  id?: number;
  nameUzLat?: string | null;
  nameUzKr?: string | null;
  nameRu?: string | null;
  nameEn?: string | null;
  icon?: string | null;
}

export class Category implements ICategory {
  constructor(
    public id?: number,
    public nameUzLat?: string | null,
    public nameUzKr?: string | null,
    public nameRu?: string | null,
    public nameEn?: string | null,
    public icon?: string | null
  ) {}
}

using RukaLjubavi.Api.Models;
using System;
using System.Collections.Generic;

namespace RukaLjubavi.Api.Services
{
    public interface IBaseDataService<TModel, TDto, TInsertModel>
           where TModel : class, IEntity
           where TDto : class
           where TInsertModel : class
    {
        void Delete(int id);
        void Delete(TModel model);
        IList<TDto> Get();
        IList<TDto> Get(Func<TModel, bool> predicate);
        TDto Get(int id);
        TDto Insert(TModel obj);
        TDto Insert(TInsertModel obj);
        void Update(TModel entity);
    }
}

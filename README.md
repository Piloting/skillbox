# 1 этап

## 1.11 Создание доменной модели бизнес-сущности

### Исходные данные


1. **Термины**  
	- Пользователь - зарегистрированный или нет пользователь системы   
	- Профиль пользователя - информация о зарегистрированном пользователе  
	- Регистрация - процесс создания профиля пользователя  
	- Друг - пользователь, между которыми установлена связь  
2. **Что делаем**  
	- Сервис Users. Это сервис, который будет управлять профилями пользователей, информацией об их увлечениях, списком друзей и контактной информацией.  
3. **Для кого и зачем**  
	- Сервис будет использовать в продукте Социальная сеть, будет хранить информацию о пользователе  
	- Сервис будет отвечать на запросы фронта. Возможно будет представлен какой-то API для других сервисов продукта или сторонних интеграций  

   
### Требования к системе на основе исходных данных:
		Варианты использования:
			Регистрация
				кто: новый пользователь укзывает минимальный набор данных для доступа к системе
				цель: писать посты, заводить друзей, читать новости
			Просмотр профиля
				кто: зарегистрированный или сторонний пользователь
				цель: изучение профиля
			Редактирование профиля
				кто: зарегистрированный пользователь
				цель: пополнение или изменение информации о себе
			Удаление профиля
				кто: зарегистрированный пользователь
				цель: завершение работы с системой
			Просмотр списка друзей
				кто: зарегистрированный пользователь
				цель: просмотр списка друзей
			Добавление друга
				кто: зарегистрированный пользователь
				цель: добавление друга для доступа к его постам
			Подтверждение друга
				кто: зарегистрированный пользователь
				цель: подтверждение запроса на добавления в друзья, разрешение просмотра своих постов
			Удаление друга
				кто: зарегистрированный пользователь
				цель: исключения пользователя из списка своих друзей
			Поиск друзей по ФИО
				кто: зарегистрированный пользователь
				цель: поиск друзей
		
		Выделение Сущностей
			Пользователь
				логин
				состояние
				дата регистрации

			Атрибуты пользователя
				пользователя
				атрибут
				значение

			Друзья
				пользователь 1
				пользователь 2
				дата создания
				состояние

			Справочник атрибутов
				название
			
			Справочник состояний пользователя
				название

			Справочник состояний дружбы
				название

		Техническое решение:
			фронт, т.е. клиент нашего сервиса, будет использован существующий
			взаимодействие по rest\https
			сервис на Java (Spring Boot)
			БД Postgrees

